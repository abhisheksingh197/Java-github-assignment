<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Welcome to the Art Collective</title>
		<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
		<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Raleway:700,600,500,400' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" type="text/css" href="/assets/css/jquery.selectbox.css" media="screen">
		<link type="text/css" rel="stylesheet" href="/assets/css/jquery.bxslider.css"  />
		<link rel="stylesheet" type="text/css" href="/assets/css/global.css" media="screen">
		<link rel="stylesheet" type="text/css" href="/assets/css/style.css" media="screen">
		<link rel="stylesheet" type="text/css" href="/assets/css/font-awesome.min.css">
		<!--[if lt IE 9]>
		<script src="/assets/js/html5shiv.min.js"></script>
		<![endif]-->
		<!--[if lt IE 8]>
		<link rel="stylesheet" type="text/css" href="/assets/css/ie7.css" media="screen">
		<link rel="stylesheet" type="text/css" href="/assets/css/ie8.css" media="screen">
		<![endif]-->
		<script type="text/javascript" src="/assets/js/jquery-1.10.2.min.js"></script>
	</head>
	<body>
		<!--Page Wrapper Start-->
		<div id="wrapper" class="artist-landing">
			<div id="overlay" class="popup-overlay" >
					<div class="popup-artist-login">
						<i class="icon-close close-popup">&nbsp;</i>
						<h4>Artist Login</h4>
						<form method="post" action="/login">
							<label>Username:</label>
							<input type="text" name="username" value="" />
							<label>Password:</label>
							<input type="password" name="password" value="" class="second"/>
							<a class="forgot-password" href="/contact-admin">Forgot Password?</a>
							<input type="submit" value="sign in" />
						</form>
					</div>
					<div class="popup-signup-membership">
						<i class="icon-close close-popup">&nbsp;</i>
						<h4>sign up for membership</h4>
						<form method="get">
							<label>Name:</label>
							<input type="text" name="name" value="" />
							<label>Email:</label>
							<input type="text" name="email" value="" />
							<label>Phone:</label>
							<input type="text" name="phone" value="" class="second"/>
							<label>Message:</label>
							<textarea name="message"> </textarea>
							<input type="submit" value="continue" />
						</form>
					</div>
					<div class="popup-sign-in">
					<i class="icon-close close-popup">&nbsp;</i>
					<div class="title">
						<h3>sign in</h3>
							<div class="new-account">
								<h5>Don't have an account?</h5>
								<a class="access-signup" href="#"><h4>Create New</h4></a>
							</div>
					</div>
					<div class="input-block">
						<form method="get">
							<label>Email:</label>
							<input type="text" name="email">
							<label>Password:</label>
							<input type="password" name="password">
							<a class="forgot-password" href="#">Forgot password?</a>
							<input class="btn" type="submit" value="SIGN IN">
						</form>
					</div>
					<div class="social-login">
						<span>Sign in through Social Media Accounts</span>
						<a href="#">
						<img alt="facebook Sign-up" src="/assets/images/facebook-singup.png" height="36" width="215">
						</a>
						<a href="#">
						<img alt="Google+ Sign-up" src="/assets/images/googleplus-signup.png" height="36" width="215">
						</a>
					</div>
				</div>
				<div class="popup-signup">
					<i class="icon-close close-popup">&nbsp;</i>
					<div class="title">
						<h3>sign up</h3>
							<div class="new-account">
								<h5>Already have an account?</h5>
								<a class="access-signin" href="#"><h4>Login</h4></a>
							</div>
					</div>
					<div class="input-block">
						<form method="get">
							<label>Name:</label>
							<input type="text" name="username">
							<label>Email:</label>
							<input type="text" name="email">
							<label>Password:</label>
							<input type="password" name="password">
							<label>Repeat:</label>
							<input type="password" name="repeat">
							<input class="btn" type="submit" value="CONTINUE">
						</form>
					</div>
					<div class="social-login">
						<span>Sign Up using Social Media Accounts</span>
						<a href="#">
						<img alt="facebook Sign-up" src="/assets/images/facebook-singup.png" height="36" width="215">
						</a>
						<a href="#">
						<img alt="Google+ Sign-up" src="/assets/images/googleplus-signup.png" height="36" width="215">
						</a>
					</div>
				</div>
				<div class="popup-reset">
					<i class="icon-close close-popup">&nbsp;</i>
					<h3>reset password</h3>
					<form>
						<label>Email:</label>
						<input type="text" name="email">
						<input class="btn" type="submit" value="reset">
					</form>
				</div>
								
				</div>
			<!--Banner Section Start-->
			<div class="container">
				<div class="banner-section">
					<ul class="bxslider">
						<li class="li1">
							<img src="/assets/images/artist-landing-banner2.jpg" alt="Banner Image" height="700" width="1280" />
							<div class="about-site">
								<h2>Art Collective</h2>
								<p>
									Art Collective’s mission is to make all the world’s art accessible to anyone with an Internet connection.
									<br />
									We are an online platform for discovering, learning about, and collecting art. Our growing collection
									<br />
									comprises 140,000+ artworks by 25,000+ artists from leading art fairs, galleries, museums, and art
									<br />
									institutions. Artsy provides one of the largest collections of contemporary art available online.
								</p>
								<div class="quick-links clearfix">
									<ul class="clearfix">
										<li>
											<div class="circle member-signup">
												<i class="icon-reg">&nbsp;</i>
											</div>
											<span>Register</span>
										</li>
										<li>
											<div class="circle">
												<i class="icon-upld">&nbsp;</i>
											</div>
											<span>Upload</span>
										</li>
										<li>
											<div class="circle">
												<i class="icon-earn">&nbsp;</i>
											</div>
											<span>Earn</span>
										</li>
									</ul>
								</div>
								<span class="button popup-signin">Artist Login</span><span class="button">Become our Partner</span>
							</div>
						</li>
						<li class="li2">
							<img src="/assets/images/artist-landing-banner.jpg" alt="Banner Image" height="700" width="1280" />
							<div class="about-site">
								<h2>Art Collective</h2>
								<p>
									Art Collective’s mission is to make all the world’s art accessible to anyone with an Internet connection.
									<br />
									We are an online platform for discovering, learning about, and collecting art. Our growing collection
									<br />
									comprises 140,000+ artworks by 25,000+ artists from leading art fairs, galleries, museums, and art
									<br />
									institutions. Artsy provides one of the largest collections of contemporary art available online.
								</p>
								<div class="quick-links clearfix">
									<ul class="clearfix">
										<li>
											<div class="circle member-signup">
												<i class="icon-reg">&nbsp;</i>
											</div>
											<span>Register</span>
										</li>
										<li>
											<div class="circle">
												<i class="icon-upld">&nbsp;</i>
											</div>
											<span>Upload</span>
										</li>
										<li>
											<div class="circle">
												<i class="icon-earn">&nbsp;</i>
											</div>
											<span>Earn</span>
										</li>
									</ul>
								</div>
								<span class="button popup-signin">Artist Login</span><span class="button">Become our Partner</span>
							</div>
						</li>
						<li class="li2">
							<img src="/assets/images/artist-landing-banner3.jpg" alt="Banner Image" height="700" width="1280" />
							<div class="about-site">
								<h2>Art Collective</h2>
								<p>
									Art Collective’s mission is to make all the world’s art accessible to anyone with an Internet connection.
									<br />
									We are an online platform for discovering, learning about, and collecting art. Our growing collection
									<br />
									comprises 140,000+ artworks by 25,000+ artists from leading art fairs, galleries, museums, and art
									<br />
									institutions. Artsy provides one of the largest collections of contemporary art available online.
								</p>
								<div class="quick-links clearfix">
									<ul class="clearfix">
										<li>
											<div class="circle member-signup">
												<i class="icon-reg">&nbsp;</i>
											</div>
											<span>Register</span>
										</li>
										<li>
											<div class="circle">
												<i class="icon-upld">&nbsp;</i>
											</div>
											<span>Upload</span>
										</li>
										<li>
											<div class="circle">
												<i class="icon-earn">&nbsp;</i>
											</div>
											<span>Earn</span>
										</li>
									</ul>
								</div>
								<span class="button popup-signin">Artist Login</span><span class="button">Become our Partner</span>
							</div>
						</li>

					</ul>
				</div>
			</div>
			<!--Banner Section End-->
			<!--Lower Content Starts-->
			<div id="lower-content">
				<!--Header Section Start-->
				<header id="header">
					<div class="container clearfix">
						<div class="header-sec-one clearfix">
							<a href="index.html" class="logo"> <img src="/assets/images/logo.png" alt="logo image" title="logo"/> </a>

							<nav>
								<ul>
									<li>
										<a href="#">about</a>
									</li>
									<li>
										<a href="#">customers</a>
									</li>
									<li>
										<a href="#">testimonials</a>
									</li>
									<li>
										<a href="#">partners</a>
									</li>

								</ul>
							</nav>
						</div>
						<div class="access-block">
							<span class="sign-in-block artist-lndg"><a href="#" class="access-signin">Sign in</a> / <a href="#" class="access-signup">Sign up</a></span>
						</div>
					</div>
				</header>
				<!--Header Section End-->

				<!--container Start-->
				<div id="content">
					<!--Content Area Start-->
					<div class="content-block">
						<section class="about-art-collective">
							<div class="page">
								<h3>&nbsp;&nbsp;&nbsp;About Art Collectives&nbsp;&nbsp;&nbsp;</h3>
								<p>
									It is a long established fact that a reader will be distracted by the
									readable content of a page when looking at its layout.
								</p>
								<div class="site-content clearfix">
								<div class="about-content">
									<p>
										Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled
										 it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially 
										 unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop
										  publishing software like Aldus PageMaker includingversions of Lorem Ipsum. experience with us.Lorem Ipsum has been the industry's standard dummy
										   text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a</p>
								</div>
								<div class="about-content">
									<p>
										Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled
										 it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially 
										 unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop
										  publishing software like Aldus PageMaker includingversions of Lorem Ipsum. experience with us.Lorem Ipsum has been the industry's standard dummy
										   text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a</p>
								</div>
								</div>
								<div class="signature-block">
									<img src="/assets/images/signature.png" alt="CEO Sign" height="79" width="150" />
									<h5>john doe</h5>
									<h5>ceo</h5>
								</div>
							</div>
						</section>
					</div>
					<div class="content-block">
						<section class="featured-section customer-section">
							<div class="page">
								<h3>&nbsp;&nbsp;&nbsp;Customers&nbsp;&nbsp;&nbsp;</h3>
								<div class="features">
									<ul class="clearfix">
										<li>
											<a href="#"><img src="/assets/images/chican-logo.png" alt="Chican Logo" height="47" width="188" /></a>
										</li>
										<li>
											<a href="#"><img src="/assets/images/nashville-logo.png" alt="Chican Logo" height="47" width="163" /></a>
										</li>
										<li>
											<a href="#"><img src="/assets/images/magazine-logo.png" alt="Chican Logo" height="47" width="190" /></a>
										</li>
										<li>
											<a href="#"><img src="/assets/images/darling-logo.png" alt="Chican Logo" height="47" width="143" /></a>
										</li>
									</ul>
								</div>
							</div>
						</section>
					</div>
					<div class="content-block">
						<section class="testimonial-section">
							<div class="page clearfix">
								<h3>&nbsp;&nbsp;&nbsp;Artist Testimonials&nbsp;&nbsp;&nbsp;</h3>
								<ul class="bxslider">
									<li>
										<div class="msg clearfix">
											<img src="/assets/images/writer-img.png" alt="writer-image" class="pic" height="128" width="128"/>
											<div class="text-msg">
												<span>“</span>
												<blockquote cite="#">
													Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ullamcorper nisi metus, a pellentesque nibh porta id. Vestibulum nulla mi, commodo sit amet massa at, viverra mollis nisi. Nulla et cursus turpis.
												</blockquote>
											</div>
										</div>
									</li>
									<li>
										<div class="msg clearfix">
											<img src="/assets/images/writer-img.png" alt="writer-image" class="pic" height="128" width="128"/>
											<div class="text-msg">
												<span>“</span>
												<blockquote cite="#">
													Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ullamcorper nisi metus, a pellentesque nibh porta id. Vestibulum nulla mi, commodo sit amet massa at, viverra mollis nisi. Nulla et cursus turpis.
												</blockquote>
											</div>
										</div>
									</li>
								</ul>
							</div>
						</section>
					</div>
					<div class="content-block last">
						<section class="featured-section customer-section">
							<div class="page">
								<h3>&nbsp;&nbsp;&nbsp;Partners&nbsp;&nbsp;&nbsp;</h3>
								<div class="features">
									<ul class="clearfix">
										<li>
											<a href="#"><img src="/assets/images/chican-logo.png" alt="Chican Logo" height="47" width="188" /></a>
										</li>
										<li>
											<a href="#"><img src="/assets/images/nashville-logo.png" alt="Chican Logo" height="47" width="163" /></a>
										</li>
										<li>
											<a href="#"><img src="/assets/images/magazine-logo.png" alt="Chican Logo" height="47" width="190" /></a>
										</li>
										<li>
											<a href="#"><img src="/assets/images/darling-logo.png" alt="Chican Logo" height="47" width="143" /></a>
										</li>
									</ul>
								</div>
							</div>
						</section>
					</div>
					<!--Content Area End-->

					<!--Footer Section Start-->
					<footer id="footer" class="clearfix">
						<div class="container">
							<nav class="footer-nav">
								<ul>
									<li>
										<a href="#" title="privacy">PRIVACY POLICY</a>
									</li>
									<li>|</li>
									<li>
										<a href="#" title="privacy">TERMS &amp; CONDITIONS</a>
									</li>
								</ul>
							</nav>
						</div>
					</footer>
					<!--Footer Section End-->

				</div>
				<!--container Start-->
			</div>
			<!-- Lower Content End -->

		</div>
		<!--Page Wrapper End-->

		<script type="text/javascript" src="/assets/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="/assets/js/jquery.selectbox-0.2.js"></script>
		<script type="text/javascript" src="/assets/js/jquery.bxslider.js"></script>
		<script type="text/javascript" src="/assets/js/site.js"></script>
	</body>
</html>