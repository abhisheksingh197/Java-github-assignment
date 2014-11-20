var addLeadForArtist = function (name, email, phoneNumber, message) {
	var lead = {"source" : "Artist Dashboard",
							"name" : name,
							"email" : email,
							"phoneNumber" : phoneNumber,
							"message" : message};
	createLead(JSON.stringify(lead), "artist");
	
};

var contactAdmin = function() {
	var formData = JSON.stringify($("#contact-form").serializeObject());
	createLead(formData, "contact-form");
};

var createLead = function(lead, requestType) {
	$.ajax({
    type:'POST',
    url: '/leads',
    data:lead,
    dataType: "text",
    contentType: "application/json; charset=utf-8",
      success:function(){
          if (requestType == "artist") {
          	var targetDiv = $(".contact-response");
          	targetDiv.html("<h6 class=\"contact-success\"> Request Successfully sent. Admin will contact you shortly.</h6>");
          	setTimeout(function(){
          		targetDiv.html("");
          		$('#overlay').fadeOut(200);
          	}, 3000);
          };
          
      },
      error: function(data){
      	if (requestType == "artist") {
      		$(".contact-response").html("<h6 class=\"contact-failure\"> Internal error. Please try after some time. </h6>");
      	}
      }
  });
};

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

