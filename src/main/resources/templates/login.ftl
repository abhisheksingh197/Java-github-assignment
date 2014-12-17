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
									Art Collective’s mission is to make Indian art accessible to anyone with an Internet connection.
									<br />
									We are dedicated to taking Indian art & Indian artists to people across the globe. Our growing collection
									<br />
									comprises 600+ artworks by 100+ renowned, young and upcoming Indian artists 
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
									Art Collective’s mission is to make Indian art accessible to anyone with an Internet connection.
									<br />
									We are dedicated to taking Indian art & Indian artists to people across the globe. Our growing collection
									<br />
									comprises 600+ artworks by 100+ renowned, young and upcoming Indian artists 
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
									Art Collective’s mission is to make Indian art accessible to anyone with an Internet connection.
									<br />
									We are dedicated to taking Indian art & Indian artists to people across the globe. Our growing collection
									<br />
									comprises 600+ artworks by 100+ renowned, young and upcoming Indian artists 
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
										<a href="#">testimonials</a>
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
									ArtCollective was started in 2014 to create a new art product for young aspirational Indians and businesses looking to elevate their homes and office spaces with Indian art. The company’s vision is to reach Indian art and art images to the masses through an affordable art platform and Giclee art prints.
								</p>
								<div class="site-content clearfix">
								<div class="about-content">
									<p>
									The ArtCollective platform was conceptualized by Meenu and Amit Jaipuria; promoters with a rich background in Indian Art.

Meenu Jaipuria founded the Mahua Art Gallery in 2002 with a vision to promote contemporary Indian art and culture. She also founded the Mahua Art Foundation to organize events to promote the awareness of India’s rich culture and heritage. Mahua Art Gallery is today one of the leading Indian art galleries in Bangalore and the Mahua Art Foundation has held over 60 events and hosted art luminaries such as Padmashree winner Dr. BN Goswamy. Meenu has been brought up surrounded by art in the family and she shares the vision of her father, Dr. HK Kejriwal, in finding new ways to reach Indian art and art awareness to the masses.

Dr. Kejriwal, Meenu’s father is one of India’s leading art patrons and collectors. He is the co-founder and vice-president at Chitra Kala Parishad (CKP), Bangalore’s most prestigious art institute. He has gifted part of his personal collection to the 20,000 sqft HK Kejriwal museum that is housed at the CKP. His donations includes works of renowned artists such as Ramkinkar Baij, Rabindranath Tagore, Amrita Sher Gil, S H Raza, Jamini Roy, MF Hussain, among others. Dr. Kejriwal’s vision has always been to promote Indian art and make artworks accessible to the masses.</p>
								</div>
								<div class="about-content">
									<p>
										Amit Jaipuria is a 3rd generation art enthusiast and technology entrepreneur. Having launched multiple retail and IT startups over the last 15 years and authored critical patents related to social networking technologies, Amit uses IT and software products to develop innovative technologies that help people in their daily lives. Amit shares the family’s belief that great art should be accessible to all and believes that technology will be the next big medium that will transform the art industry.

As a family, Amit and Meenu share a common goal – one to inspire Indians through the creations of Indian artists and artworks and make affordable and within reach of more Indians.</p>
								</div>
								</div>
								<div class="signature-block">
									
									<h5>Meenu & Amit Jaipuria</h5>
									
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
											
											<div class="text-msg">
												<span>“</span>
												<blockquote cite="#">
													I have sold my painting's print through art collective without any problems and their services have always been very transparent.  I am very happy to work with ArtCollective, and with Mahua Gallery, they are the best platform for young artists to showcase their talent and reach out to more people.
												</blockquote>
											</div>
										</div>
									</li>
									<li>
										<div class="msg clearfix">
											
											<div class="text-msg">
												<span>“</span>
												<blockquote cite="#">
													ArtCollective has been transparent in their dealings with the artists. They encourage to do better and even small bit of information is shared on time. Like they inform me when my prints are up online and whenever I earn a commission. Updates are shared timely as well. All the best to the team.
												</blockquote>
											</div>
										</div>
									</li>
									<li>
										<div class="msg clearfix">
											
											<div class="text-msg">
												<span>“</span>
												<blockquote cite="#">
													I am very excited and happy to be part of India's first affordable art platform. I have never received such attention and appreciation for my work. Being a young artist, it is very encouraging to see such people willing to help us out.
												</blockquote>
											</div>
										</div>
									</li>
								</ul>
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
										© 2014 ArtCollective India Pvt. Ltd.
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