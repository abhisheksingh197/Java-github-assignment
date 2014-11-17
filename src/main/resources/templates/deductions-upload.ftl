<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Welcome to the Art Collective</title>
		<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
		<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Raleway:700,600,500,400' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" type="text/css" href="/assets/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="/assets/css/ustm-radio-check.css" media="screen">
		<link rel="stylesheet" type="text/css" href="/assets/css/jquery.selectbox.css" media="screen">
		<link rel="stylesheet" type="text/css" href="/assets/css/global.css" media="screen">
		<link rel="stylesheet" type="text/css" href="/assets/css/style.css" media="screen">
		<!--[if lt IE 9]>
		<link rel="stylesheet" type="text/css" href="/assets/css/ie8.css" media="screen">
		<script src="/assets/js/html5shiv.min.js"></script>
		<![endif]-->
		<!--[if lt IE 8]>
		<link rel="stylesheet" type="text/css" href="/assets/css/ie7.css" media="screen">
		<![endif]-->
		<script type="text/javascript" src="/assets/js/jquery-1.10.2.min.js"></script>
	</head>
	<body>
		<!--Page Wrapper Start-->
		<div id="wrapper" class="artist-dashboard">
			<!--Header Section Start-->
			<header id="header">
				<div class="container clearfix">
					<div class="header-sec-one">
						<div class="logo">
							<a href="index.html"> <img src="/assets/images/logo.png" alt="logo image" width="201" height="48" /> </a>
						</div>
							
					</div>
					<div class="access-block">
						<div class="login">
							<a class="profile-image" href="#"><img src="/assets/images/artist-login-img.png" alt="login-img" width="38" height="38" /></a>
						</div>
					</div>
				</div>
			</header>

			<!--Header Section End-->
			<div class="container upload-container">
				 <div class="upload-content">
				 	<h3 class="upload-heading"> Artist Deduction Upload </h3>
					<div>
						<input type="file" id="file-input" />
						<a class="button" onclick="uploadCSV('deductions');"> Upload File </a>
					</div>	
					<div id="import-success">
						
					</div>
					<pre>
						<div class="payment-table intrl-tab errors-table ">
		                    <table>
		                        <thead>
		                            <tr>
		                                <td class="amt-paid">Error Message</td>
		                            </tr>
		                        </thead>
		                        <tbody id="errors-list">
		                        </tbody>
		                    </table>
		                 </div>
	                 </pre>
		         </div>
		     </div>


				

		<!--Page Wrapper End-->

		<script type="text/javascript" src="/assets/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="/assets/js/jquery.screwdefaultbuttonsV2.js"></script>
		<script type="text/javascript" src="/assets/js/jquery.selectbox-0.2.js"></script>
		<script type="text/javascript" src="/assets/js/masonry.pkgd.js"></script>
		<script type="text/javascript" src="/assets/js/modernizr.js"></script>
		<script type="text/javascript" src="/assets/js/site.js"></script>
		<script type="text/javascript" src="/assets/js/uploadCSV.js"></script>
		<script type="text/javascript">
			$container = $('#gallery-search');
			// initialize
			$container.masonry({
				//columnWidth: 38.5,
				itemSelector : '.objects'
			});

		</script>
	</body>
</html>