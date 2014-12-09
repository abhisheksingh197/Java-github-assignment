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
		<link rel="stylesheet" type="text/css" href="/assets/css/tablet.css">
		<link rel="stylesheet" type="text/css" href="/assets/css/mobile.css">
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
		<div id="wrapper">
			<div id="overlay" class="popup-overlay" >
				<div class="share-popup">
					<i class="icon-close close-popup">&nbsp;</i>
					<span>Share this art on your favourite Social Media Platform</span>
					<div class="social-share">
						<ul class="clearfix">
							<li>
								<a href="#" class="icon-sharett" title="twitter-share">&nbsp;</a>
							</li>
							<li>
								<a href="#" class="icon-sharepi" title="Pintrest-share">&nbsp;</a>
							</li>
							<li>
								<a href="#" class="icon-sharefb" title="fb-share">&nbsp;</a>
							</li>
							<li>
								<a href="#" class="icon-sharegp" title="g+-share">&nbsp;</a>
							</li>
						</ul>
					</div>
				</div>
			
			</div>

			<!--Header Section Start-->

			<header id="header">
				<div class="container clearfix">
					<div class="header-sec-one">
						<div class="logo">
							<a href="${homePageURL}"> <img src="/assets/images/logo.png" alt="logo image" width="201" height="48" /> </a>
						</div>
						<nav>
							<ul>
							<li>
								<a href="search-result.html">Art Finder</a>
							</li>
							<li>
								<a href="collection.html">collections</a>
							</li>
							<li>
								<a href="subjects.html">subjects</a>
							</li>
							<li>
								<a href="colors.html">color</a>
							</li>
							<li>
								<a href="style.html">styles</a>
							</li>
							<li>
								<a href="artist.html">artists</a>
							</li>
						</ul>
						</nav>
					</div>
					<div class="access-block">
						<span class="cart-text call-us">Call Us: <a href="tel:0123456789">0 123 456 789</a></span>
						<a class="cart-header" href="#"><i class="icon-cart"><span id="cart-count" class="cart-count">0</span></i><span class="cart-text">Cart</span></a>
						<div class="login">
							<a class="profile-image" href="#"><img src="/assets/images/artist-login-img.png" alt="login-img" width="38" height="38" /></a>
							<div class="header-dropdown">
								<ul>
									<li>
										<a href="recommended.html"><i class="icon-recommended">&nbsp;</i>Recommended</a>
									</li>
									<li>
										<a href="favorites.html"><i class="icon-favorites">&nbsp;</i>Favorites</a>
									</li>
									<li>
										<a href="#"><i class="icon-preferences">&nbsp;</i>Preferences</a>
									</li>
									<li>
										<a href="order.html"><i class="icon-orders">&nbsp;</i>Orders</a>
									</li>
									<li><a href="following.html"><i class="following-bg">&nbsp;</i>Following</a></li>
									<li>
										<a href="setting.html"><i class="icon-setting">&nbsp;</i>Settings</a>
									</li>
									<li>
										<a href="#"><i class="icon-signout">&nbsp;</i>Sign out</a>
									</li>
								</ul>
								<small class="fa fa-sort-asc">&nbsp;</small>
							</div>
						</div>
					</div>
				</div>
			</header>
			<!--Header Section End-->

			<!--Content Area Start-->
			<div id="content" class="search-result-content">
				<div class="recommended-overlap"></div>
				<div class="container clearfix">
					<div class="aside">
						<div class="aside-content">
							<div class="search clearfix">
								<input type="submit" value="" class="icon-search trigger-pannel" />
								<input type="text" value="Search"  onfocus="if(this.value==this.defaultValue)this.value='';" onblur="if(this.value=='')this.value=this.defaultValue;"/>
							</div>
							<div class="block-wrap clearfix">

								<div class="block clearfix">
									<div class="headlines clearfix">
										<i class="icon-tag trigger-pannel">&nbsp;</i>
										<h6 class="catgry">CATEGORIES: All</h6>
										<i class="icon-click">&nbsp;</i>

									</div>

									<ul class="list accordian-content category-spl">

										<li>
											<a href="#">Fine Art</a>
										</li>
										<li>
											<a href="#">Digital Art</a>
										</li>
										<li>
											<a href="#">Photography</a>
										</li>

									</ul>

								</div>

								<div class="block clearfix">
									<div class="headlines clearfix">
										<i class="icon-price trigger-pannel">&nbsp;</i>
										<h6>PRICE RANGE</h6>
										<i class="icon-click">&nbsp;</i>

									</div>

									<ul class="list amount accordian-content">

										<li>
											<a href="#">Rs. 2500 - Rs. 5000</a>
										</li>
										<li>
											<a href="#">Rs. 5001 - Rs. 7500</a>

										</li>
										<li>
											<a href="#">Rs. 7501 - Rs. 10,000</a>
										</li>
										<li>
											<a href="#">Rs. 10,001 - Rs. 15000</a>
										</li>
										<li>
											<a href="#">Rs. 15001 AND ABOVE</a>
										</li>

									</ul>

								</div>

								<div class="block clearfix">
									<div class="headlines clearfix">
										<i class="icon-colour trigger-pannel">&nbsp;</i>
										<div class="filter-color clearfix">
											<h6>COLOR  (max 2)</h6>
											<ul class="color-filter">
												<li class="blue">&nbsp;</li>
												<li class="green">&nbsp;</li>
											</ul>
										</div>
										<i class="icon-click">&nbsp;</i>

									</div>

									<div class="color-range accordian-content">
										<div class="color-picker-steps">
										<h3>Step 1</h3>
										<span>Select a color</span>
									</div>
										<div class="description">
											<ul class="colors">
												<li class="light-purple">
													 <img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
												</li>
												<li class="dark-purple">
													 <img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
												</li>
												<li class="blue">
													 <img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
												</li>
												<li class="green">
													 <img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
												</li>
												<li class="yellow">
													<img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
												</li>

												<li class="orange">
													<img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
													<i class="col-selector">&nbsp;</i>
												</li>
												<li class="red">
													 <img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
												</li>
												<li class="white">
													<img class="tick-sign" src="/assets/images/tick-sign-white.png" alt="click" width="17" height="13" />
												</li>
												<li class="black">
													 <img class="tick-sign" src="/assets/images/tick-sign.png" alt="click" width="17" height="13" />
												</li>
											</ul>
											<div class="shade-block">
												<div class="color-picker-steps">
												<h3>Step 2</h3>
												<span>Select a shade</span>
											</div>
												<ul class="shades">
													<li class="FFF7E6">
														&nbsp;
													</li>
													<li class="FFEECC">
														&nbsp;
													</li>
													<li class="FFE6B2">
														&nbsp;
													</li>
													<li class="FFDD99">
														&nbsp;
													</li>
													<li class="FFD580">
														&nbsp;
													</li>
													<li class="FFCD66">
														&nbsp;
													</li>
													<li class="FFC44D">
														&nbsp;
													</li>
													<li class="FFBC33">
														&nbsp;
													</li>
													<li class="FFB319">
														&nbsp;
													</li>
													<li class="ffab00">
														&nbsp;
													</li>
													<li class="E69A00">
														&nbsp;
													</li>
													<li class="CC8900">
														&nbsp;
													</li>
													<li class="B27800">
														&nbsp;
													</li>
													<li class="L996700">
														&nbsp;
													</li>
													<li class="L805600">
														&nbsp;
													</li>
												</ul>
											</div>

											<div class="color-mix">
											
												<span class="colors1">&nbsp;</span>
												<span class="add-more-color">You can select one more colour</span>
											</div>
										</div>

									</div>

								</div>

								<div class="block clearfix">
									<div class="headlines clearfix">
										<i class="icon-sub trigger-pannel">&nbsp;</i>
										<h6>SUBJECT</h6>
										<i class="icon-click">&nbsp;</i>

									</div>

									<ul class="list accordian-content category-spl">

										<li>
											<a href="#">Abstract &amp; conceptual (78)</a>
										</li>
										<li>
											<a href="#">Animals  &amp; birds (394)</a>

										</li>
										<li>
											<a href="#">Architecture  &amp; cityscapes</a>
										</li>
										<li>
											<a href="#">Flowers &amp; plants (153)</a>
										</li>
										<li>
											<a href="#">Landscapes, sea &amp; sky (1023)</a>
										</li>
										<li>
											<a href="#">Nudes &amp; erotic (415)</a>
										</li>
										<li>
											<a href="#">People &amp; portraits (201)</a>
										</li>
									</ul>
									<select class="select" >
										<option> Select tag </option>
										<option> Select tag </option>
										<option> Select tag </option>
										<option> Select tag </option>
									</select>
								</div>

								<div class="block clearfix">
									<div class="headlines clearfix">
										<i class="icon-st trigger-pannel">&nbsp;</i>
										<h6>STYLE</h6>
										<i class="icon-click">&nbsp;</i>

									</div>

									<ul class="list accordian-content category-spl">

										<li>
											<a href="#">Abstract (645)</a>

										</li>
										<li>
											<a href="#">Geometric (812)</a>
										</li>
										<li>
											<a href="#">Organic (361)</a>
										</li>
										<li>
											<a href="#">Collage, composite (34)</a>
										</li>
										<li>
											<a href="#">Expressive  &amp; gestural (92)</a>
										</li>
										<li>
											<a href="#">Graphic (153)</a>
										</li>

									</ul>
								</div>

								<div class="block clearfix">
									<div class="headlines clearfix">
										<i class="icon-medium trigger-pannel">&nbsp;</i>
										<h6>MEDIUM</h6>
										<i class="icon-click">&nbsp;</i>

									</div>

									<ul class="list accordian-content category-spl">

										<li>
											<a href="#">Water Color (645)</a>

										</li>
										<li>
											<a href="#">Oil Painiting (812)</a>
										</li>
										<li>
											<a href="#">Pencil (361)</a>
										</li>
										<li>
											<a href="#">Pen &amp; Ink (153)</a>
										</li>
										<li>
											<a href="#">Fabric (153)</a>
										</li>
										<li>
											<a href="#">Charcol (153)</a>
										</li>

									</ul>
								</div>

								<div class="block clearfix">
									<div class="headlines clearfix">
										<i class="icon-or trigger-pannel">&nbsp;</i>
										<h6>ORIENTATION</h6>
										<i class="icon-click">&nbsp;</i>

									</div>

									<ul class="list accordian-content custom-radio-field category-spl">
										<li>
											<input type="radio" name="mode" id="rdo1" />
											<label for="rdo1" >All </label>
										</li>
										<li>
											<input type="radio" name="mode" id="rdo2" />
											<label for="rdo2"><i class="landscape">&nbsp;</i>Landscape</label>
										</li>
										<li>
											<input type="radio" name="mode" id="rdo3" />
											<label for="rdo3"><i class="portrait">&nbsp;</i>Portrait</label>
										</li>
										<li>
											<input type="radio" name="mode" id="rdo4" />
											<label for="rdo4"><i class="square">&nbsp;</i>Square</label>
										</li>

									</ul>
									<select class="select">
										<option>Select size</option>
										<option>Select size</option>
										<option>Select size</option>
										<option>Select size</option>
										<option>Select size</option>
									</select>

								</div>

							</div>
						</div>
					</div>

					<div class="main-content slide-container">
						<i id="animate-pannel" class="icon-cross">&nbsp;</i>
						<div class="gallery following-content clearfix">
							<div class="following-heading">
								<h3>Preferences</h3>
								<div class="search-btn">
									<a href="#" class="btn">Save</a>
									<a href="#" class="btn">Search Now</a>
								</div>
							</div>
							<div class="purchase-process following-check-list">
								<section class="connect clearfix">
									<div class="dropdown">
										<h3 class="accordian-up">categories</h3>
									</div>
									<div class="accordion clearfix">
										<ul class="check-list-type clearfix">
											<li>
												<input type="checkbox" id="chk1" />
												<label for="chk1">Fine Art</label>
											</li>
											<li>
												<input type="checkbox" id="chk2" />
												<label for="chk2">Digital Art</label>
											</li>
											<li>
												<input type="checkbox" id="chk3" checked="checked" />
												<label for="chk3">Photography</label>
											</li>
											
										</ul>
									</div>
								</section>

								<section class="address-block clearfix">
									<div class="dropdown">
										<h3>subject</h3>
									</div>
									<div class="accordion clearfix">
										<ul class="check-list-type clearfix">
										<#assign keysinUserPreferencesMap = userPreferences?keys>
										<#list shopPreferences.subjects as subject>
											<li class="clearfix">
												<#assign hasKeyValue = keysinUserPreferencesMap?seq_contains(subject.id)?string("yes", "no") />
												<input type="checkbox" id="${subject.id?c}" class="chk_${subject_index + 1}" <#if hasKeyValue == "yes">checked="checked" </#if>/>
												<label class="camel-case" for="chk_${subject_index + 1}">${subject.title}</label>
											</li>
										</#list>
										</ul>
									</div>
								</section>
								<section class="order-summary clearfix">
									<div class="dropdown">
										<h3>STYLE</h3>
									</div>
									<div class="accordion clearfix">
										<ul class="check-list-type clearfix">
										<#list shopPreferences.styles as style>
											<li class="clearfix">
												<#assign hasKeyValue = keysinUserPreferencesMap?seq_contains(style.id)?string("yes", "no") />
												<input type="checkbox" id="${style.id?c}" class="chk_${style_index + 1}" <#if hasKeyValue == "yes">checked="checked" </#if>/>
												<label class="camel-case" for="chk_${style_index + 1}">${style.title}</label>
											</li>
										</#list>
										</ul>
									</div>
								</section>
								<section class="payment-block clearfix">
									<div class="dropdown">
										<h3>medium</h3>
									</div>
									<div class="accordion clearfix">
										<ul class="check-list-type artist_check-list clearfix">
											<#list shopPreferences.mediums as medium>
											<li class="clearfix">
												<#assign hasKeyValue = keysinUserPreferencesMap?seq_contains(medium)?string("yes", "no") />
												<input type="checkbox" id="${medium}" class="chk_${medium_index + 1}" <#if hasKeyValue == "yes">checked="checked" </#if>/>
												<label class="camel-case" for="chk_${medium_index + 1}">${medium}</label>
											</li>
										</#list>
										</ul>
									</div>
								</section>
								<section class="payment-block clearfix">
									<div class="dropdown">
										<h3>orientation</h3>
									</div>
									<div class="accordion clearfix">
										<ul class="check-list-type artist_check-list clearfix">
											<#list shopPreferences.orientations as orientation>
											<li class="clearfix">
												<#assign hasKeyValue = keysinUserPreferencesMap?seq_contains(orientation)?string("yes", "no") />
												<input type="checkbox" id="${orientation}" class="chk_${orientation_index + 1}" <#if hasKeyValue == "yes">checked="checked" </#if>/>
												<label class="camel-case" for="chk_${orientation_index + 1}">${orientation}</label>
											</li>
										</#list>
										</ul>
									</div>
								</section>
							</div>
						</div>

						<!--Footer Section Start-->
						<footer id="footer" class="clearfix share-art">
							<div class="page">
								<div class="footer-sec">
									<div class="footer-one clearfix">
										<div class="list collections">
											<h4>COLLECTIONS</h4>
											<ul>

												<li>
													<a href="#">OTW Artists (200)</a>
												</li>
												<li>
													<a href="#">Angles and Symmetry (415)</a>
												</li>
												<li>
													<a href="#">Portrait (1023)</a>
												</li>
												<li>
													<a href="#">Spotlight on Berlin (79)</a>
												</li>
												<li>
													<a href="#">The Painted Nude (394)</a>
												</li>
												<li>
													<a href="#">Sleeping Beauty (13)</a>
												</li>
												<li>
													<a href="#">Really Real (153)</a>
												</li>
												<li>
													<a href="#">Nature (984)</a>
												</li>
												<li>
													<a href="#">Horses (361)</a>
												</li>
												<li>
													<a href="#">Deep Blue Sea (874)</a>
												</li>
												<li>
													<a href="#">Surrealism (201)</a>
												</li>
												<li>
													<a href="#">Cityscapes (812)</a>
												</li>
												<li>
													<a href="#">Nude Photography (645)</a>
												</li>
												<li>
													<a href="#">Green With Envy (78)</a>
												</li>
												<li>
													<a href="#"><span>View all </span><i class="icon-view-all">&nbsp;</i></a>
												</li>
											</ul>
										</div>
										<div class="list subject">
											<h4>SUBJECT</h4>
											<ul>

												<li>
													<a href="#">Abstract &amp; conceptual (78)</a>
												</li>
												<li>
													<a href="#">Animals &amp; birds (394)</a>
												</li>
												<li>
													<a href="#">Architecture &amp; cityscapes</a>
												</li>
												<li>
													<a href="#">Flowers &amp; plants (153)</a>
												</li>
												<li>
													<a href="#">Landscapes, sea &amp; sky (1023)</a>
												</li>
												<li>
													<a href="#">Nudes &amp; erotic (415)</a>
												</li>
												<li>
													<a href="#">People &amp; portraits (201)</a>
												</li>
												<li>
													<a href="#">Still life (79)</a>
												</li>
												<li>
													<a href="#">Transportation &amp; maps (504)</a>
												</li>
												<li>
													<a href="#"><span>View all </span><i class="icon-view-all">&nbsp;</i></a>
												</li>
											</ul>
										</div>
										<div class="list style">
											<h4>STYLE</h4>
											<ul>

												<li>
													<a href="#">Abstract (645)</a>
												</li>
												<li>
													<a href="#">Geometric (812)</a>
												</li>
												<li>
													<a href="#">Organic (361)</a>
												</li>
												<li>
													<a href="#">Collage, composite</a>
												</li>
												<li>
													<a href="#">Expressive &amp; gestural</a>
												</li>
												<li>
													<a href="#">Graphic (153)</a>
												</li>
												<li>
													<a href="#">Typographic (394)</a>
												</li>
												<li>
													<a href="#">Illustrative (79)</a>
												</li>
												<li>
													<a href="#">Cartoon (645)</a>
												</li>
												<li>
													<a href="#">Impressionistic (13)</a>
												</li>
												<li>
													<a href="#">Naive (79)</a>
												</li>
												<li>
													<a href="#">Realistic &amp; photographic (812)</a>
												</li>
												<li>
													<a href="#">Surrealistic (78)</a>
												</li>
												<li>
													<a href="#">Unspecified (504)</a>
												</li>
												<li>
													<a href="#">Urban &amp; pop art (756)</a>
												</li>
												<li>
													<a href="#"><span>View all </span><i class="icon-view-all">&nbsp;</i></a>
												</li>
											</ul>
										</div>
										<div class="list photography">
											<h4>PHOTOGRAPHY</h4>
											<ul>

												<li>
													<a href="#">Color Photography (415)</a>
												</li>
												<li>
													<a href="#">Black &amp; White (1023)</a>
												</li>
												<li>
													<a href="#">Manipulated (812)</a>
												</li>
												<li>
													<a href="#">Landscape (394)</a>
												</li>
												<li>
													<a href="#">Surreal Photography (415)</a>
												</li>
												<li>
													<a href="#">Abstract Photography (201)</a>
												</li>
												<li>
													<a href="#">Cities (834)</a>
												</li>
												<li>
													<a href="#">Portraits (876)</a>
												</li>
												<li>
													<a href="#"><span>View all </span><i class="icon-view-all">&nbsp;</i></a>
												</li>
											</ul>
										</div>
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

					</div>

				</div>

			</div>
			<!--Content Area End-->

			<!--container Start-->

		</div>
		<!--Page Wrapper End-->

		<script type="text/javascript" src="/assets/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="/assets/js/jquery.screwdefaultbuttonsV2.js"></script>
		<script type="text/javascript" src="/assets/js/jquery.selectbox-0.2.js"></script>
		<script type="text/javascript" src="/assets/js/masonry.pkgd.js"></script>
		<script type="text/javascript" src="/assets/js/modernizr.js"></script>
		<script type="text/javascript" src="/assets/js/site.js"></script>
		<script type="text/javascript">
			$container = $('#gallery-search');
			// initialize

			$(window).load(function() {
				$container.masonry({
					//columnWidth: 38.5,
					itemSelector : '.objects'
				});
			});

			$('#gallery-search img').load(function() {

				$container.masonry('destroy');

				$container.masonry({
					//columnWidth: 38.5,
					itemSelector : '.objects'
				});
			})

		</script>
	</body>
</html>