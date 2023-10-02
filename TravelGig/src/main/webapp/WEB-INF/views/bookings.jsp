<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %> 

<head>
    <meta charset="ISO-8859-1">
    <title>Bookings</title>
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="./js/bookings.js"></script>
    <style>
        .table th,
        .table td {
            text-align: center;
            vertical-align: middle;
        }

        .table th,
        .table td {
            padding: 0.5rem;
        }

        .booking-nav-link {
                font-size: 18px; 
                padding: 5px 10px; 
                text-decoration: none; 
                border-radius: 5px;
                margin-right: 10px; 
                margin-left:10px;
        }

        .active-link {
            background-color: #007BFF; 
            color: #fff; 
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mt-3">Bookings</h1>
        <h4 class="mb-4">Update or cancel bookings here</h4>
        <%@ include file ="menu.jsp" %><br>
    </div>

    <div class="d-flex justify-content-center">
        <a href="#" id="activeBookingsLink" class="booking-nav-link active-link">Active Bookings</a> 
        <a href="#" id="completedBookingsLink" class="booking-nav-link">Completed Bookings</a> 
        <a href="#" id="cancelledBookingsLink" class="booking-nav-link">Cancelled Bookings</a>
    </div>
    </p></p>

    <table id="activeBookings" class="table table-striped">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Booking Id</th>
                <th scope="col">Check-In Date</th>
                <th scope="col">Check-Out Date</th>
                <th scope="col">Number of Rooms</th>
                <th scope="col">Price</th>
                <th scope="col">Room Type</th>
                <th scope="col">Status</th>
                <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody id="activeBookingTblBody">
        </tbody>
    </table>

    <table id="completedBookings" class="table table-striped" style="display: none;">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Booking Id</th>
                <th scope="col">Check-In Date</th>
                <th scope="col">Check-Out Date</th>
                <th scope="col">Number of Rooms</th>
                <th scope="col">Price</th>
                <th scope="col">Room Type</th>
                <th scope="col">Status</th>
                <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody id="completedBookingTblBody">
        </tbody>
    </table>

    <table id="cancelledBookings" class="table table-striped" style="display: none;">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Booking Id</th>
                <th scope="col">Check-In Date</th>
                <th scope="col">Check-Out Date</th>
                <th scope="col">Number of Rooms</th>
                <th scope="col">Price</th>
                <th scope="col">Room Type</th>
                <th scope="col">Status</th>
                <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody id="cancelledBookingTblBody">
        </tbody>
    </table>

    <div class="modal" id="reviewModal">
        <div class="modal-dialog">
          <div class="modal-content">
      
            <div class="modal-header">
              <h4 class="modal-title">Write a Review</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
      
            <div class="modal-body">        
              <div class="col">
                  <input class="form-control" type="hidden" id="modal_bookingId"/>
                  <label for="modal_reviewText">Review:</label>
                  <textarea class="form-control" id="modal_reviewText" rows="4"></textarea>
                  <label for="modal_rating">Rating (1-5):</label>
                  <input class="form-control" type="number" id="modal_rating" name="modal_rating" min="1" max="5">

                  <input style="margin-top:25px" class="btn btn-saveReview form-control btn-primary" type="button" id="modal_saveReviewButton" value="SAVE REVIEW"/>       	
              </div>
              
            </div>
      
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
            </div>
      
          </div>
        </div>
      </div>

</body>
</html>
