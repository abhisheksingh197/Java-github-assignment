var uploadCSV = function(uploadType) {
	var formData = new FormData();
	var fileInput = $('#file-input');
  formData.append('file', fileInput.get(0).files[0]);
  var fileName = fileInput.get(0).files[0].name;
  if(TestFileType( fileName, ['.csv'] )) {
  	$.ajax({
      type:'POST',
	    url: '/api/upload/' + uploadType,
	    data:formData,
	      cache:false,
	      contentType: false,
	      processData: false,
	      success:function(apiResponse){
	          generateErrorsList(apiResponse);
	      },
	      error: function(data){
	          console.log("error");
	          console.log(data);
	      }
	  });
  } else {
  	alert("Upload only file of type '.csv'");
  	resetFileElement(fileInput);
  }
  
};

function TestFileType( fileName, fileTypes ) {
	if (!fileName) return;

	dots = fileName.split(".");
	fileType = "." + dots[dots.length-1];

	return (fileTypes.join(".").indexOf(fileType) != -1) ? true : false;
}

var generateErrorsList = function(errors) {
	console.log(errors);
	var errorsListBody = "";
	for (error in errors) {
		errorsListBody += "<tr><td><pre> " + errors[error] + "<pre></td></tr>";
	}
	var ifErrors = "";
	if (errors.length > 0) {
		ifErrors = "The following records were rejected";
		$("#errors-list").html(errorsListBody);
		$(".payment-table").css("display", "inline-block");
	}
	$("#import-success").html("<p class=\"import-success\"> Data Imported Successfully..! " +
	 ifErrors + ". </p>");
	
};

function resetFileElement(ele) 
{
    ele.val(''); 
    ele.wrap('<form>').parent('form').trigger('reset');   
    ele.unwrap();
    ele.prop('files')[0] = null;
    ele.replaceWith(ele.clone());    
}

