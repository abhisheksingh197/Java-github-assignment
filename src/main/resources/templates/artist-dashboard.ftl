
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Welcome to the Art Collective</title>
        <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
        <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:700,600,500,400' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="assets/css/ustm-radio-check.css" media="screen">
        <link rel="stylesheet" type="text/css" href="assets/css/jquery.selectbox.css" media="screen">
        <link rel="stylesheet" type="text/css" href="assets/css/global.css" media="screen">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css" media="screen">
        <!--[if lt IE 9]>
        <link rel="stylesheet" type="text/css" href="assets/css/ie8.css" media="screen">
        <script src="assets/js/html5shiv.min.js"></script>
        <![endif]-->
        <!--[if lt IE 8]>
        <link rel="stylesheet" type="text/css" href="assets/css/ie7.css" media="screen">
        <![endif]-->
        <script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
    </head>
    <body>
        <!--Page Wrapper Start-->
        <div id="wrapper" class="artist-dashboard">
            <div id="overlay" class="popup-overlay" >
                <div class="popup-contact">
                    <i class="icon-close close-popup">&nbsp;</i>
                    <h4>contact admin</h4>
                    <form method="get">
                        <label>Name</label>
                        <input type="text" name="name" value="" />
                        <label>Email</label>
                        <input type="text" name="email" value="" />
                        <label>Phone</label>
                        <input type="text" name="phone" value="" class="second"/>
                        <label>Message</label>
                        <textarea name="message"> </textarea>
                        <input class="btn" type="submit" value="send message" />
                    </form>
                </div>
                <div class="popup-addmore-work">
                    <i class="icon-close close-popup">&nbsp;</i>
                    <h2>I’m Interested to add more works</h2>
                    <a href="#" class="button">please contact me</a>
                </div>
            </div>
            <!--Header Section Start-->
            <header id="header">
                <div class="container clearfix">
                    <div class="header-sec-one">
                        <div class="logo">
                            <a href="index.html"> <img src="assets/images/logo.png" alt="logo image" width="201" height="48" /> </a>
                        </div>
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
                        <div class="login">
                            <a class="profile-image" href="#"><img src="assets/images/artist-login-img.png" alt="login-img" width="38" height="38" /></a>
                        </div>
                    </div>
                </div>
            </header>

            <!--Header Section End-->

            <!--Content Area Start-->
            <div id="content">
                <div class="container clearfix">
                    <div class="tab-container clearfix">

                        <ul class="tab-links">
                            <li class="active">
                                <i class="icon-tab">&nbsp;</i>Portfolio
                            </li>
                            <li>
                                <i class="icon-tab">&nbsp;</i>Earnings
                            </li>
                            <li class="contact-admin">
                                <i class="icon-tab">&nbsp;</i>Contact Admin
                            </li>
                            <li>
                                <i class="icon-tab">&nbsp;</i>Sign Out
                            </li>
                        </ul>
                        <div class="tab-content clearfix portfolio-tab">
                            <div>
                                <h3>portfolio</h3>
                                <div class="contact-info-block clearfix">
                                    <img src="${artist.imgSrc}" alt="artist" height="72" width="72" />
                                    <div class="contact-info">
                                        <h4>${artist.firstName} ${artist.lastName}</h4>
                                        <a href="mailto:${artist.email!}?Subject=Contact%20Artist" target="_top" title="mail to artist">${artist.email!}</a>
                                        <span>${artist.contactNumber!}</span>
                                    </div>
                                </div>
                                <div class="add-work-block clearfix">
                                    <a href="#" class="more-work">add more works</a>
                                </div>
                            </div>
                            <#assign artworksLength = artworks?size>
                            <div class="portfolio-block clearfix">
                                <h4>There are ${artworksLength} works of your portfolio</h4>
                                <table>
                                    <thead>
                                        <tr>
                                            <td>artwork image</td>
                                            <td class="print">print</td>
                                            <td class="size">size</td>
                                            <td class="earning">earning</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    
                                    	<#list 0..(artworksLength - 1) as artIterator>
                                    		<#assign artwork = (artworks[artIterator]) />
                                    		<#assign variantList = artwork.variants />
	                                        <tr>
	                                        	<#assign imageSrc = artwork.images[0].imgSrc />
	                                        	<#assign croppedImgSrc = imageSrc?split("_") />
	                                        	<#assign croppedImgSrc = croppedImgSrc[0] + "_O_small.jpg" />
	                                            <td><img src="${croppedImgSrc}" alt="portfolio" height="59" width="72"/></td>
	                                            <td>
	                                            	<h5>${artwork.title}</h5>
	                                            	<h5>${artwork.description!}</h5>
	                                            </td>
                                            	<td>
                                            		<#list variantList as variant>
                                        				<h6>${variant.option1}</h6>
                                            		</#list>
                                            	</td>
                                            	<td>
                                            		<#list variantList as variant>
                                            			<#assign li = (lineitems[variant.id?c])!0.0 />
                                        				<h6>${li?string("0.00")}</h6>
                                            		</#list>
                                            	</td>
	                                        </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="tab-content clearfix earning-tab">
                            <div>
                                <h3>earnings</h3>
                                <div class="contact-info-block clearfix">
                                    <img src="${artist.imgSrc}" alt="artist" height="72" width="72" />
                                    <div class="contact-info">
                                        <h4>${artist.firstName} ${artist.lastName}</h4>
                                        <a href="mailto:${artist.email!}?Subject=Contact%20Artist" target="_top" title="mail to artist">${artist.email!}</a>
                                        <span>${artist.contactNumber!}</span>
                                    </div>
                                </div>
                            </div>
                            <div class="earning-tab-content">
                                <span class="info">Click Button to view details</span>
                                <ul>
                                    <li class="button" rel="earning-tab2">
                                        earnings
                                        <span class="earn-count">${dashboardValues.netCommission}</span>
                                    </li>
                                    <li class="button" rel="payout-tab2">
                                        payouts
                                        <span class="payout-count">${dashboardValues.payouts}</span>
                                    </li>
                                    <li class="button" rel="pending-tab2">
                                        pending
                                        <span class="pending-count">${dashboardValues.pending}</span>
                                    </li>
                                </ul>
                            </div>
                            <div class="earning-tab2 intrl-tab">
                                <ul>
                                    <li rel="earn-table">
                                        <span class="earn-titles">total commission</span>
                                        <span class="button">${dashboardValues.totalEarningsAsCommission}</span>
                                        <span class="earn-details">View details</span>
                                    </li>
                                    <li rel="deduction-table">
                                        <span class="earn-titles">total deductions</span>
                                        <span class="button">${dashboardValues.totalDeductions}</span>
                                        <span class="earn-details">View details</span>
                                    </li>
                                    <li>
                                        <span class="earn-titles">net commission</span>
                                        <span class="button">${dashboardValues.netCommission}</span>
                                    </li>
                                </ul>
                            </div>
                            <div class="earn-table intrl-tab">
                                <table>
                                    <thead>
                                        <tr>
                                            <td>date</td>
                                            <td>order id</td>
                                            <td class="align-center">artwork image</td>
                                            <td class="align-center">size</td>
                                            <td>quantity</td>
                                            <td>commission</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<#list earningsList as earningLineItem>
                                        <tr>
                                            <td>${earningLineItem.orderDate.toString('MMM dd, yyyy')!}</td>
                                            <td>${earningLineItem.orderName!}</td>
                                            <td class="align-center"><img src="${earningLineItem.productImageSrc!}!" alt="earnings" height="48" width="48"/></td>
                                            <td class="align-center">${earningLineItem.variantSize!}</td>
                                            <td>${earningLineItem.quantity!}</td>
                                            <td>${earningLineItem.commission!}</td>
                                        </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                            <div class="deduction-table intrl-tab">
                                <table>
                                    <thead>
                                        <tr>
                                            <td>date</td>
                                            <td>artwork</td>
                                            <td>item</td>
                                            <td class="align-center">cost</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1 July 2015</td>
                                            <td><img src="assets/images/deduction1.jpg" alt="deduction" height="48" width="48"/></td>
                                            <td>Photography</td>
                                            <td class="align-center">250</td>
                                        </tr>
                                        <tr>
                                            <td>1 July 2015</td>
                                            <td><img src="assets/images/deduction2.jpg" alt="deduction" height="48" width="48"/></td>
                                            <td>Photography</td>
                                            <td class="align-center">250</td>
                                        </tr>
                                        <tr>
                                            <td>1 July 2015</td>
                                            <td><img src="assets/images/deduction3.jpg" alt="deduction" height="48" width="48"/></td>
                                            <td>Photography</td>
                                            <td class="align-center">250</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="payout-tab2 intrl-tab">
                                <ul>
                                    <li rel="payment-table">
                                        <span class="earn-titles">total payment</span>
                                        <span class="button">50000</span>
                                        <span class="earn-details">View details</span>
                                    </li>
                                    <li rel="payment-table">
                                        <span class="earn-titles">total pending</span>
                                        <span class="button">20000</span>
                                        <span class="earn-details">View details</span>
                                    </li>
                                </ul>
                            </div>
                            <div class="payment-table intrl-tab">
                                <table>
                                    <thead>
                                        <tr>
                                            <td class="amt-paid">date</td>
                                            <td class="amt-paid">amount paid</td>
                                            <td class="amt-paid">payment details</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1 July 2015</td>
                                            <td class="payment-amt">Rs 15,000</td>
                                            <td class="payment-dtls">NEFT #1234564567</td>
                                        </tr>
                                        <tr>
                                            <td>1 July 2015</td>
                                            <td class="payment-amt">Rs 7,600</td>
                                            <td class="payment-dtls">AQRS 789534889485</td>
                                        </tr>
                                        <tr>
                                            <td>1 July 2015</td>
                                            <td class="payment-amt">Rs 33,678</td>
                                            <td class="payment-dtls">NEFT #123456</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="pending-tab2 intrl-tab">
                                <ul>
                                    <li class="active">
                                        <span class="earn-titles">total pending</span>
                                        <span class="button">20000</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="tab-content">

                        </div>
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
        <!--Content Area End-->

        <!--container Start-->

        </div>
        <!--Page Wrapper End-->

        <script type="text/javascript" src="assets/js/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="assets/js/jquery.screwdefaultbuttonsV2.js"></script>
        <script type="text/javascript" src="assets/js/jquery.selectbox-0.2.js"></script>
        <script type="text/javascript" src="assets/js/masonry.pkgd.js"></script>
        <script type="text/javascript" src="assets/js/modernizr.js"></script>
        <script type="text/javascript" src="assets/js/site.js"></script>
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