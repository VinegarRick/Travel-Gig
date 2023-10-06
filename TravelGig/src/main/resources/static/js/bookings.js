$(document).ready(function() {
	
    $("#activeBookings").show();
    $("#completedBookings").hide();
    $("#cancelledBookings").hide();
    
    $("#activeBookingsLink").click(function() {
        $("#activeBookings").show();
        $("#completedBookings").hide();
        $("#cancelledBookings").hide();
        
        $(".booking-nav-link").removeClass("active-link");
        $(this).addClass("active-link");
    });

    $("#completedBookingsLink").click(function() {
        $("#activeBookings").hide();
        $("#completedBookings").show();
        $("#cancelledBookings").hide();
        
        $(".booking-nav-link").removeClass("active-link");
        $(this).addClass("active-link");
    });

    $("#cancelledBookingsLink").click(function() {
        $("#activeBookings").hide();
        $("#completedBookings").hide();
        $("#cancelledBookings").show();
        
        $(".booking-nav-link").removeClass("active-link");
        $(this).addClass("active-link");
    });
	
	function isCurrentDateAfterCheckoutDate(currentDateStr, checkoutDateStr) {
	  const currentDate = new Date(currentDateStr);
	  const checkoutDate = new Date(checkoutDateStr);
	
	  return currentDate > checkoutDate;
	}

	
	function getCurrentDate() {
	  const today = new Date();
	  const year = today.getFullYear();
	  const month = String(today.getMonth() + 1).padStart(2, '0'); 
	  const day = String(today.getDate()).padStart(2, '0');
	
	  const formattedDate = `${year}-${month}-${day}`;
	
	  return formattedDate;
	}
	
	$.ajax({
		type:"GET",
		contentType: "application/json",
		url: "http://localhost:8282/findAllBookingsByUsername",
		success: function(result) {
			$.each(result, function(key1, value1) {
		        
		        var currentDate = getCurrentDate();
		        if (value1.status != "COMPLETED" && value1.status != "CANCELLED" && isCurrentDateAfterCheckoutDate(currentDate, value1.checkOutDate)) {
					value1.status = "COMPLETED";
					var status = value1.status;
					var bookingId = value1.bookingId;
					
					$.ajax({
						type:"POST",
						contentType: "application/json",
						url: "http://localhost:8282/updateBookingStatus",
						data: JSON.stringify({bookingId: bookingId, status: status}),
						dataType: 'json',
						success: function(data) {
							console.log('Success:', data);
						},
						error: function(e) {
							console.error('Error:', e);
						},
					})			
					
				}
				
				//var cancelUrl = "cancelBooking.html?id=" + value1.bookingId;
				//var reviewUrl = "writeReview.html?id=" + value1.bookingId;
			    var buttonHtml = "";
			    if (value1.status === "COMPLETED") {
			        buttonHtml = "<button class='review-button'>Review</button>";
			        $("#completedBookingTblBody").append("<tr><td>" + value1.bookingId + "</td><td>" + value1.checkInDate + "</td><td>" + value1.checkOutDate + "</td><td>" + value1.noRooms + "</td><td>" + value1.price + "</td><td>" + value1.roomType + "</td><td>" + value1.status + "</td><td>" + buttonHtml + "</td></tr>");
			    } else if (value1.status === "CANCELLED") {
					buttonHtml = "";
					$("#cancelledBookingTblBody").append("<tr><td>" + value1.bookingId + "</td><td>" + value1.checkInDate + "</td><td>" + value1.checkOutDate + "</td><td>" + value1.noRooms + "</td><td>" + value1.price + "</td><td>" + value1.roomType + "</td><td>" + value1.status + "</td><td>" + buttonHtml + "</td></tr>");
				} else {
			        buttonHtml = "<button class='cancel-button'>Cancel</button>";
			        $("#activeBookingTblBody").append("<tr><td>" + value1.bookingId + "</td><td>" + value1.checkInDate + "</td><td>" + value1.checkOutDate + "</td><td>" + value1.noRooms + "</td><td>" + value1.price + "</td><td>" + value1.roomType + "</td><td>" + value1.status + "</td><td>" + buttonHtml + "</td></tr>");
			    }
		        
				//$("#bookingTblBody").append("<tr><td>" + value1.bookingId + "</td><td>" + value1.checkInDate + "</td><td>" + value1.checkOutDate + "</td><td>" + value1.noRooms + "</td><td>" + value1.price + "</td><td>" + value1.roomType + "</td><td>" + value1.status + "</td><td><a href='" + updateUrl + "' class='update-link'>Update</a> | <a href='" + deleteUrl + "' class='delete-link'>Delete</a></td></tr>");
				//$("#bookingTblBody").append("<tr><td>" + value1.bookingId + "</td><td>" + value1.checkInDate + "</td><td>" + value1.checkOutDate + "</td><td>" + value1.noRooms + "</td><td>" + value1.price + "</td><td>" + value1.roomType + "</td><td>" + value1.status + "</td><td>" + buttonHtml + "</td></tr>");
			});
		},
		error: function(e) {
			
		},
	})
	

    $("#activeBookingTblBody").on("click", ".cancel-button", function(e) {
        e.preventDefault(); 

        var bookingId = $(this).closest("tr").find("td:first").text();

        var requestData = {
            bookingId: bookingId,
            status: "CANCELLED"
        };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8282/updateBookingStatus",
            data: JSON.stringify(requestData),
            dataType: "json",
            success: function(data) {
                $(this).closest("tr").find("td:nth-child(7)").text("CANCELLED");
                var $row = $(this).closest("tr");
                $(this).remove();
                $row.clone().appendTo("#cancelledBookings tbody");
                $row.remove();
                console.log("booking cancelled");
                alert("Booking cancelled");
            }.bind(this), 
            error: function(e) {
                console.error("Error:", e);
            }
        });
    });
    
    $("#completedBookingTblBody").on("click", ".review-button", function(e) {
        e.preventDefault();

        var bookingId = $(this).closest("tr").find("td:first").text();
        $("#modal_bookingId").val(bookingId);
        
        $('#modal_reviewText').val(''); 
        $('#modal_rating').val(''); 

        // Open the review modal
        $("#reviewModal").modal("show");
    });

    $("#modal_saveReviewButton").click(function() {
        var bookingId = $("#modal_bookingId").val();
        var reviewText = $("#modal_reviewText").val();
        var rating = $("#modal_rating").val();
        
        var requestData = {
            review: {overallRating: rating, text: reviewText},
            bookingId: bookingId
        };
        
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8282/saveReview",
            data: JSON.stringify(requestData),
            dataType: "json",
            success: function(data) {
				$(this).remove();
				console.log("review saved");
				alert("Review saved!");
            }.bind(this), 
            error: function(e) {
                console.error("Error:", e);
            }
        });

        $("#reviewModal").modal("hide");
    });
	
});