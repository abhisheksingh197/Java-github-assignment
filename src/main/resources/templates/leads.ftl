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
							<a href="${homePageURL}"> <img src="/assets/images/logo.png" alt="logo image" width="201" height="48" /> </a>
						</div>
							
					</div>
					
				</div>
			</header>

			<!--Header Section End-->
			<div class="container upload-container">
				 <div class="upload-content">
				 <h3 class="upload-heading"> Leads </h3>
					
						<div class="payment-table intrl-tab leads-table ">
		                    <table>
		                        <thead>
		                            <tr>
		                                <td class="amt-paid">Date</td>
		                                <td class="amt-paid">Source</td>
		                                <td class="amt-paid">Name</td>
		                                <td class="amt-paid">Email</td>
		                                <td class="amt-paid">Phone Number</td>
		                                <td class="amt-paid">Message</td>
		                            </tr>
		                        </thead>
		                        <tbody id="errors-list">
		                        <#list leads as lead>
		                        	<tr>
		                        		<td> ${lead.createdAt.toString('MMM dd, yyyy')!} </td>
		                        		<td> ${lead.source!} </td>
		                        		<td> ${lead.name!} </td>
		                        		<td> ${lead.email!} </td>
		                        		<td> ${lead.phoneNumber!} </td>
		                        		
		                        		<td><pre> ${lead.message!} </pre></td>
		                        	</tr>
		                        </#list>
		                        </tbody>
		                    </table>
		                 </div>
	                 
		         </div>
		     </div>


				

		<!--Page Wrapper End-->
		
		<!--Footer Section Start-->
				<footer id="footer" class="clearfix share-art">
					<div class="page">
						<div class="footer-sec">
							<div class="footer-one clearfix">
								
							</div>
							<div class="page">
								<div class="follow-section">
									<div class="footer-two clearfix">

										<div class="social-links">
											<ul>
												<li>
													<a href="#"><i class="icon-fb">&nbsp;</i></a>
												</li>
												<li>
													<a href="#"><i class="icon-gplus">&nbsp;</i></a>
												</li>
												<li>
													<a href="#"><i class="icon-instagram">&nbsp;</i></a>
												</li>
												<li>
													<a href="#"><i class="icon-pintrest">&nbsp;</i></a>
												</li>
												<li>
													<a href="#"><i class="icon-twitter">&nbsp;</i></a>
												</li>
											</ul>
										</div>
										<nav class="footer-nav">

											<ul>
												<li>
													<a href="#">About</a>
												</li>
												<li>
													<a href="#">Contact</a>
												</li>

												<li>
													<a href="#">Terms of use</a>
												</li>

												<li>
													<a href="#">Privacy Policy</a>
												</li>
											</ul>
										</nav>
									</div>
								</div>
								<div class="copyright-section">
									<span>&copy; 2014 Art Collective. All rights reserved.</span>
								</div>
							</div>
						</div>

					</div>
				</footer>
				<!--Footer Section End-->

		<script type="text/javascript" src="/assets/js/jquery-1.10.2.min.js"></script>
	</body>
</html>