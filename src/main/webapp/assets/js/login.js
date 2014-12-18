var makeRequest = function() {
	var resetFormBlock = $('#resetForm');
	var errorMessageElement = $('#reset-message');
	var emailValue = $.trim(resetFormBlock.find('input[name="email"]').val());
	var lead = {
      "name": emailValue,
      "email": emailValue,
      "phoneNumber": "",
      "message": "Reset Password",
      "source": "Artist Login"
   };
	if (errorMessageElement.data('valid') === false) {
		errorMessageElement.html('Please enter a valid Email-id');
		return false;
  } else {
    $.ajax({
      type:'POST',
      url: '/leads',
      data:JSON.stringify(lead),
      dataType: "text",
      contentType: "application/json; charset=utf-8",
      success:function(){   
      	return true;
      },
      error: function(data){
        $('#reset-message').html('Error : Could not send request now! Please try later.'); 
      }
    });
  }
};

$(".close-popup").click(function() {
	$('#reset-message').html('');
});

var validateText = function() {
	var resetFormBlock = $('#resetForm');
	var emailValue = $.trim(resetFormBlock.find('input[name="email"]').val());
	var errorMessageElement = $('#reset-message');
	if (emailValue !== '' && validateEmail(emailValue)) {
		errorMessageElement.html('');
		errorMessageElement.data('valid', true);
	} else {
		errorMessageElement.html('Please enter a valid Email-id');
		errorMessageElement.data('valid', false);
	}
};

var validateEmail = function(emailAddress) {
  var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
  return filter.test(emailAddress);
};