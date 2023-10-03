$(document).ready(function() {
	
	$("#searchBtn").click(function() {
		var searchLocation = $("#searchLocation").val();
		$.ajax({
			type:"GET",
			contentType: "application/json",
			url: "http://localhost:8282/findHotel/" + searchLocation,
			success: function(result) {
				$("#hotelTbl tr").not(".header").remove();			
				$.each(result, function(key1, value1) {
					/*$("#hotelTbl").append("<tr class='hotelInfo'><td>" + value1.hotelName + "</td><td>" + value1.averagePrice + "</td><td>" + value1.starRating + "</td><td><img class='imgLink' height='300' width='300' src='"+ value1.imageURL + "'></td></tr>");*/
					
					var amenityNames = value1.amenities.map(function(amenity) {
		                return amenity.name;
		            }).join(', ');
		            //console.log(amenityNames);
		            
		            //console.log(value1.hotelRooms.length);
		            
		            /*var hotelRoomTypes = value1.hotelRooms.map(function(room) {
		                return room.type.name;
		            }).join(', ');
		            console.log(hotelRoomTypes);*/
		            
					$("#hotelTbl").append("<tr class='hotelInfo' data-hotel-id='" + value1.hotelId + "'><td>" + value1.hotelName + "</td><td><img class='imgLink' height='300' width='300' src='"+ value1.imageURL + "'></td><td>" + value1.averagePrice + "</td><td>" + value1.starRating + "</td><td>" + amenityNames + "</td></tr>");	
			        
			        //console.log(value1);
				});
	        },		
			error: function(e) {
				
			},
		})
		
	});

	$("#filterBtn").click(function() {
		var priceRange = parseInt($("#priceRange").val());
		var tblRow = $("#hotelTbl tr").not(".header");
		$(tblRow).show();
		$.each(tblRow, function(key1, value1) {
			var flag = 0;
			var ratingFilterFlag = 0;
			var amenitiesFilterFlag = 0;
			var hotelPrice = parseInt($(value1).children("td").eq("2").text());
			var hotelRating = parseInt($(value1).children("td").eq("3").text());
			var hotelAmenities = $(value1).children("td").eq("4").text().toUpperCase();
			
			if (hotelPrice > priceRange) {
				$(this).hide();
			} else {
			
				$.each($(".star_rating"), function(key1, value) {
					if ($(this).prop('checked') == true) {
						ratingFilterFlag= 1;
						var starRating = parseInt($(this).val());
						
						if (starRating == hotelRating) {
							flag = 1;
						}
					
					}
				});
				
				if (ratingFilterFlag == 1 && flag == 0) {
					$(this).hide();
				} else {
					$.each($(".hotel_amenity"), function(key, value) {
						if ($(this).prop('checked') == true) {
							amenitiesFilterFlag = 1;
							var amenityName = $(this).val();
							
							if (!hotelAmenities.includes(amenityName)) {
								flag = 0;
								return false;
							}
						}
					})
					
					if (amenitiesFilterFlag == 1 && flag == 0) {
						$(this).hide();
					}
				}
			}
		});
	})
	
	function populateRoomTypes(hotelId) {
		var roomTypeMap = {};
	    $.ajax({
	        type: "GET",
	        url: "/findHotelRoomsByHotelId/" + hotelId, // Updated URL
	        dataType: "json",
	        success: function (data) {
	            var selectRoomTypes = $("#select_roomTypes");
	            selectRoomTypes.empty(); // Clear existing options
	
	            // Populate options with received data
	            data.forEach(function (hotelRoom) {
					var hotelRoomId = hotelRoom.hotelRoomId;
					var price = hotelRoom.price;
					var discount = hotelRoom.discount;
	                var roomType = hotelRoom.type.name; // Extract room type name
	                selectRoomTypes.append($("<option></option>")
	                    .attr("value", roomType)
	                    .text(roomType)
	                );
	                
	                roomTypeMap[roomType] = {
						hotelRoomId: hotelRoomId, 
						price: price,
						discount: discount
					}
	            });
	            
	            selectRoomTypes.data("roomTypeMap", roomTypeMap);
	        },
	        error: function (error) {
	            console.log("Error fetching room types: " + JSON.stringify(error));
	        }
	    });
	}

	
	$("#hotelTbl").on('click', '.imgLink', function() {
		var hotelId = $(this).closest('.hotelInfo').data("hotel-id");
		var hotelName = $(this).closest('.hotelInfo').find('td:eq(0)').text();
		var noGuests = $("#noGuests").val();
		var noRooms = $("#noRooms").val();
		var checkInDate = $("#checkInDate").val();
		var checkOutDate = $("#checkOutDate").val();
		
		$("#modal_hotelId").val(hotelId);
	    $("#modal_hotelName").val(hotelName);
	    $("#modal_noGuests").val(noGuests);
	    $("#modal_noRooms").val(noRooms);
	    $("#modal_checkInDate").val(checkInDate);
	    $("#modal_checkOutDate").val(checkOutDate);	
	    
	    populateRoomTypes(hotelId);
	    
		$("#myModal").modal("toggle");
	})
	
	$("#myModal").on('click', '#modal_moveToGuests', function() {
		//var noGuests = $("#modal_noGuests").val();
		//$("#guests_noGuests").val(noGuests);
		
		// consider moving to $("#searchBtn").click
		$("#guestsModal input[type='text'], #guestsModal input[type='number'], #guestsModal select").val('');
		
		$("#myModal").modal("hide");
		$("#guestsModal").modal("toggle");
	});
	
	$("#myModal").on('click', '#modal_moveToReviews', function() {		
		var hotelId = $("#modal_hotelId").val();
		var hotelName = $("#modal_hotelName").val();
		
		//$("review_hotelName").val(hotelName);
		
	    $.ajax({
	        type: "GET",
	        contentType: "application/json",
	        url: "http://localhost:8282/findReviewsByHotelId/" + hotelId,
	        success: function(reviews) {
	            var modalBody = $("#reviewsModal .modal-body");
	            modalBody.empty();
	
	            if (reviews.length > 0) {
	                $.each(reviews, function(index, review) {
						
						$.ajax({
							type: "GET",
							contentType: "application/json",
							url: "http://localhost:8282/findBookingByReviewId/" + review.reviewId,
							success: function(booking) {
								modalBody.append("<p><strong>Date:</strong> " + booking.checkOutDate + "</p>");
								//modalBody.append("<strong>Date:</strong> " + booking.checkOutDate);
								//modalBody.append("<p><strong>User:</strong> " + booking.userName + "</p>");
								modalBody.append("<strong>User:</strong> " + booking.userName + "<br>");
			                    modalBody.append("<strong>Rating:</strong> " + review.overallRating + "<br>");
			                    //modalBody.append("<p><strong>Review:</strong> " + review.text + "</p>");
								modalBody.append("<strong>Review:</strong><br>");
								modalBody.append("<span>" + review.text + "</span>");


			                    modalBody.append("<hr>");
							},
							error: function() {
								console.error("Error fetching bookings corresponding to reviews");
							}
						})
						
	                });
	            } else {
	                modalBody.append("<p>No reviews available for this hotel.</p>");
	            }
	
	            $("#reviewsModal").modal("show");
	        },
	        error: function() {
	            console.error("Error fetching reviews.");
	        }
	    });
		
	});
	
    $("#guestsModal").on('click', '#modal_moveToBooking', function() {
		var hotelId = $("#modal_hotelId").val();
        var hotelName = $("#modal_hotelName").val();
        var noGuests = $("#modal_noGuests").val();
        var noRooms = $("#modal_noRooms").val();
        var checkInDate = $("#modal_checkInDate").val();
        var checkOutDate = $("#modal_checkOutDate").val();
        var roomType = $("#select_roomTypes").val();
        
		var selectRoomTypes = $("#select_roomTypes");
		var roomTypeMap = selectRoomTypes.data("roomTypeMap");
		var roomType = selectRoomTypes.val();
		var roomData = roomTypeMap[roomType];
		var price = roomData.price;
		var discount = roomData.discount;

		$("#booking_hotelId").val(hotelId);
        $("#booking_hotelName").val(hotelName);
        $("#booking_noGuests").val(noGuests);
        $("#booking_noRooms").val(noRooms);
        $("#booking_checkInDate").val(checkInDate);
        $("#booking_checkOutDate").val(checkOutDate);
        $("#booking_roomType").val(roomType);
		$("#booking_discount").text(discount * noRooms);
		$("#booking_price").text((price - discount) * noRooms);


		$("#guestsModal").modal("hide");
        $("#bookingHotelRoomModal").modal("toggle");
    });
    
    $("#bookingRoom_modalBody").on('click', '#modal_confirmBooking', function() {
		var selectRoomTypes = $("#select_roomTypes");
		var roomTypeMap = selectRoomTypes.data("roomTypeMap");
		var roomType = selectRoomTypes.val();
		var roomData = roomTypeMap[roomType];
		var hotelRoomId = roomData.hotelRoomId;
		var price = roomData.price;
		var discount = roomData.discount;
		
		var hotelId = $("#modal_hotelId").val();
		var noRooms = $("#modal_noRooms").val();
		var noGuests = $("#modal_noGuests").val();
		var checkInDate = $("#modal_checkInDate").val();
		var checkOutDate = $("#modal_checkOutDate").val();
		var bookedOnDate = (function() {
		    const today = new Date();
		    const dd = String(today.getDate()).padStart(2, '0');
		    const mm = String(today.getMonth() + 1).padStart(2, '0');
		    const yyyy = String(today.getFullYear());

    		return yyyy + '-' + mm + '-' + dd;			
		})();
				
		var status = "UPCOMING";
		var customerMobile = $("#booking_customerMobile").val();
		
		// handle userName and userEmail fetch in the controller with principal
		
		const guests = [];
		
		let guest = {
			age : $("#guest1_age").val(), 
			firstName : $("#guest1_firstName").val(),
			gender : $("#guest1_select_gender").val(),
			lastName : $("#guest1_lastName").val()
		};
		
		guests.push(guest);
		
		if (noGuests > 1) {
			let guest2 = {
				age : $("#guest2_age").val(), 
				firstName : $("#guest2_firstName").val(),
				gender : $("#guest2_select_gender").val(),
				lastName : $("#guest2_lastName").val()
			};	
			guests.push(guest2);
		}
		
		var bookingData = {
			bonanzaDiscount: 0.0,
			bookedOnDate: bookedOnDate, 
			checkInDate: checkInDate, 
			checkOutDate: checkOutDate, 
			customerMobile: customerMobile, 
			discount: discount, 
			finalCharges: price * noRooms,
			hotelId: hotelId, 
			hotelRoomId: hotelRoomId, 
			noRooms: noRooms,
			price: price * noRooms,
			roomType: roomType,
			status: status,
			taxRateInPercent: 0.0,
			totalSavings: discount * noRooms, 
			//userEmail: "",
			//username: "",
			review_ReviewId: -1
		}
		
		var requestData = {
			bookingData: bookingData, 
			guests: guests,
		}
		
		$.ajax({
			type:"POST",
			contentType: "application/json",
			url: "http://localhost:8282/saveBooking",
			data: JSON.stringify(requestData),
			dataType: 'json',
			success: function(data) {
				console.log('Success:', data);
			},
			error: function(e) {
				console.error('Error:', e);
			},
		})
		
		$("#booking_customerMobile").val(''); 
		$("#bookingHotelRoomModal").modal("hide");
	});
	
});