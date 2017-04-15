

$( document ).ready(function() {
    $( ".chat-request" ).click(function(e) {
      e.preventDefault();
      $('.open-request').html('Matthew Townsen - TeamSupport').removeClass('open-request')
      $(this).html('<p>Name:  Matthew Townsen</p>' +
                       '<p>Email:  mtownsen@teamsupport.com</p>' +
                       '<p>Time:  2:47 PM</p>' +
                       '<p>Message:  Its all broken</p>' +
                       '<button class="btn btn-default">Accept</button>')
                       .addClass('open-request');
    });
});