(function($) {
	var top1;
	$(window).load(function() {
		$(".access-signin").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$(".popup-sign-in").show();
		})
		$(".access-signup").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$(".popup-signup").show();
		})
		$(".forgot-password").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$(".popup-reset").show();
		})
		$(".open-share").click(function() {
			var topset = $(this).closest('.image-block').offset().top;
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.share-popup').css({
				"display" : 'block',
				// 'top' : topset
				 
			})
		})
		$('.login').hover(function(){
			$('.recommended-overlap').show();
		},function(){
			
			$('.recommended-overlap').hide();
		})
		$(".contact-admin").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.popup-contact').show()
		})
		$(".more-work").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.popup-addmore-work').show()
		})
		$('.tab-links li').click(function() {
			var ind = $(this).index();
			$('.tab-links li').removeClass('active');
			$(this).addClass('active');
			$('.tab-content').hide();
			$('.tab-content').eq(ind).show();
		})
		$('.team-info.tab-links li').click(function() {
			var ind = $(this).index();
			var prop = "1px solid #b0b0b0";
			$('.team-info.tab-links li').removeClass('active');
			$(this).addClass('active');
			$('.team-info.tab-links').css('border-bottom',prop);
			$('.tabs-content').hide();
			$('.tabs-content').eq(ind).fadeIn(500);
		})
		$('.tab-content ul li.button').click(function() {
			$('.tab-content ul li.button').removeClass('active');
			$(this).addClass('active');
			$('.intrl-tab').hide();
			var class_name = $(this).attr('rel');
			$('.' + class_name).show();
		})
		$('.earning-tab2 li').click(function() {
			$('.earning-tab2 li').removeClass('active');
			$(this).addClass('active');
			$('.intrl-tab').hide();
			var class_name = $(this).attr('rel');
			$('.' + class_name).show();
			$('.earning-tab2').show()
		})
		$('.payout-tab2 li').click(function() {
			$('.payout-tab2 li').removeClass('active');
			$(this).addClass('active');
			$('.intrl-tab').hide();
			var class_name = $(this).attr('rel');
			$('.' + class_name).show();
			$('.payout-tab2').show()
		})
		$("#add-addr").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.popup-address').show()
		})
		$(".payment-btn").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.confirm-order').show()
		})
		$(".close-popup").click(function() {
			$('#overlay').fadeOut(200)
		})
		$('.read-more').click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.read-more-popup').show();
		})
		$('.popup-signin').click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.popup-artist-login').show();
		})
		$('.member-signup').click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.popup-signup-membership').show();
		})
		$('.dropdown').click(function() {
			$(this).next().slideToggle();
			$(this).find('i').toggleClass('fa fa-check-circle');
			var ind = $(this).closest('section').index();
			$('.purchase-progress ul li').eq(ind).css('opacity', 1);
		})
		$('.colors').click(function() {
			$('.shade-block').show();
		})
		$('.colors li').click(function() {
			$('.tick-sign').css('display', 'none')
			$('.col-selector').css('display', 'none')
			$('.color-filter').css('display','block')
			$(this).children('.tick-sign').css('display', 'block')
			$(this).children('.col-selector').css('display','block')
		})
		$('.shades').click(function() {
			$('.color-info').css('visibility', 'visible')
			$('.color-mix').show();
			$('#page-st3').addClass('active-bg');
		})
		$('.following-check-list h3').click(function() {
			$(this).toggleClass('accordian-up')
		})
		$('.order-process h3').click(function() {
		
			$(this).toggleClass('no-gap')
		})
		$(".upload").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.popup-upload-img').show()
		})
		$('.color-picker .description .colors li').click(function() {
			$('.tick-sign').css('display', 'none')
			$(this).children('.tick-sign').css('display', 'block')
			$('#page-st2').addClass('active-bg');
		})
		$('.colors-popups').click(function() {
			$('.shade-block-popup').show();
		})
		$('.color-picker .description .colors-popups li').click(function() {
			$('.tick-sign').css('display', 'none')
			$(this).children('.tick-sign').css('display', 'block')
		})
		$('.shades-popups').click(function() {
			$('.color-mix-popups').show();
		})
		top1 = $('#header').offset().top;
		$('.order-range li, .available-frame > li').click(function() {
			$(this).siblings().removeClass('active');
			$(this).addClass('active');
			$('.available-frame-heading').show();
			$('.available-frame').slideDown();
			if ($(this).children('.frame-options').length == 1 && !($(this).children('.frame-options').hasClass('active2'))) {
				$('.available-frame > li').children('.frame-options').removeClass('active2').slideUp();
				$(this).children('.frame-options').addClass('active2').slideDown();
			}
			if (($(this).hasClass('blank-content'))) {
				$('.available-frame > li').children('.frame-options').removeClass('active2').slideUp();
			}
		})
		$(".select").selectbox();
		
		
		
		$('.available-frame > li').click(function() {
			if ($(this).index() == 1) {
				$('#carousel').flexslider({
					animation : "slide",
					controlNav : false,
					animationLoop : false,
					slideshow : false,
					itemWidth : 38,
					itemMargin : 20,
					asNavFor : '#slider'
				});
				$('#slider').flexslider({
					animation : "slide",
					controlNav : false,
					animationLoop : false,
					slideshow : false,
					sync : "#carousel",
					start : function(slider) {
						$('body').removeClass('loading');
					}
				});
			}
		})
		if ($(".fancybox-thumb").length > 0)
			$(".fancybox-thumb").fancybox();
		$("#add-cart").click(function() {
			$('#overlay').fadeIn(500)
			$('#overlay').children().hide();
			$('.addtocart-popup').show()
		})
	})
	mentaionFirstSection();
	$(".headlines").click(function() {
		$(".accordian-content").slideUp(400)
		if ($(this).hasClass('active')) {
			$('.active').removeClass('active');
			$(this).next().slideUp(400);
		} else {
			$(this).addClass('active');
			$(this).next().slideDown(400);
		}
	})
	$("#animate-pannel").click(function() {
		animate();
	})
	$(".tab_content").hide();
	$("ul.tabs li:first").addClass("active").show();
	$(".tab_content:first").show();
	$("ul.tabs li").click(function() {
		$("ul.tabs li").removeClass("active");
		$(this).addClass("active");
		$(".tab_content").hide();
		var activeTab = $(this).find("a").attr("href");
		$(activeTab).fadeIn();
		return false;
	});
	$('.collection-content .load-more-btn a').click(function(e) {
		e.preventDefault();
		for (var i = 0; i < 4; i++) {
			var item = $('.art-collection.macr .art-collection-inner').eq(i);
			$('.art-collection.macr').append($('<li>').attr('class', 'art-collection-inner').html($(item.html())))
		}
	})
	$('.mobile-menu').click(function() {
		$('.header-sec-one nav').slideToggle(400)
	})
	$('.icon-likes').click(function() {
		$(this).css('background-position', '-23px -95px')
	})
	$('.follow-artist a').click(function() {
		$(this).children('i.icon-follow').css('background-position', '-29px -101px')
		$(this).css('color','#96D1F0')
	})
	$('.paint-sec a').click(function() {
		$(this).children('i').css('color', '#96D1F0')
	})
	if ($('input:radio').length > 0) {
		$('input:radio').screwDefaultButtons({
			image : 'url("assets/images/type-radio.png")',
			width : 13,
			height : 13
		});
	}

	if($('input:checkbox').length){
		$('input:checkbox').screwDefaultButtons({
		image : 'url("assets/images/check-box.png")',
		width : 16,
		height : 16
	});
	}

	$('.trigger-pannel').click(function() {
		if ($('#animate-pannel').hasClass('slide-btn')) {
			$('#animate-pannel').trigger('click');
		}
	});
	$('.load-more-option.mas > .btn').click(function(e) {
		e.preventDefault()
		if ($(this).parent().siblings('.gallery-wrap').hasClass('gallery-wrap1')) {
			for (var i = 0; i < 10; i++) {
				var item = $('.gallery-wrap .objects').eq(i);
				$('.gallery-wrap').append($('<div>').attr('class', 'objects').html($(item.html())))
			}
			animateResize();
		} else {
			for (var i = 0; i < 8; i++) {
				var item = $('.gallery-wrap .objects').eq(i);
				$('.gallery-wrap').append($('<div>').attr('class', 'objects').html($(item.html())))
			}
		}
		$container = $('#gallery-search');
		$container.masonry('destroy');
		$container.masonry({
			itemSelector : '.objects'
		});
	})

	$(window).resize(function() {
		mentaionFirstSection();
		animateResize();
	})
	$(window).scroll(function() {
		var scroll = $(this).scrollTop();
		if (scroll > top1) {
			$('#header').addClass('fixed-header')
		} else {
			$('#header').removeClass('fixed-header')
		}
	})
	function mentaionFirstSection() {
		$('.banner-section').css({
			'height' : $(window).height(),
		})
	}

	function animate() {
		wid = parseInt($('.search-result-content .container').width()) - 42;
		objectWidth();
		if (!$('#animate-pannel').hasClass('slide-btn')) {
			$('.accordian-content').hide();
			$('.accordian-content').prev('.headlines').removeClass('active')
			$('#animate-pannel').addClass('slide-btn')
			$('.aside-content').addClass('aside-width');
			$('.aside').css('overflow', 'hidden');
			if($(window).width()<1005){
				$('.aside').animate({
				'width' : 32 + 'px'
			}, 400, function() {
			})
			}
			else{
				$('.aside').animate({
				'width' : 42 + 'px'
			}, 400, function() {
			})
			}
			$('.slide-container').animate({
				'width' : wid
			}, 400, function() {
				$container.masonry('destroy');
				$container = $('#gallery-search');
				$container.masonry({
					itemSelector : '.objects'
				});
			})
			$('.block').addClass('content-icon');
		} else {
			$('.accordian-content').hide();
			$('.accordian-content').prev('.headlines').removeClass('active')
			$('.block').removeClass('content-icon');
			$('#animate-pannel').removeClass('slide-btn');
			$('.aside-content').removeClass('aside-width');
			$('.aside').css('overflow', '');
			$('.aside').animate({
				'width' : 24.2 + '%'
			}, 400, function() {
			})
			$('.slide-container').animate({
				'width' : 75.7 + '%'
			}, 400, function() {
				$container.masonry('destroy');
				$container = $('#gallery-search');
				$container.masonry({
					itemSelector : '.objects'
				});
			})
		}
	}

	function animateResize() {
		objectWidthResize();
		wid = parseInt($('.search-result-content .container').width()) - 42;
		if ($(window).width() < 768) {
			if ($('#animate-pannel').hasClass('slide-btn')) {
				$('.block').removeClass('content-icon');
				$('.slide-container').css('width', wid);
				$('.aside-content').removeClass('aside-width');
				$('.aside').width('100%')
			} else {
				$('.block').removeClass('content-icon');
				$('.aside').width('100%')
			}
		}
		else if(($(window).width()) < 1013 && ($(window).width() > 768) ){
			wid=wid+10;
			if ($('#animate-pannel').hasClass('slide-btn')) {
				$('.block').removeClass('content-icon');
				$('.slide-container').css('width', wid);
				$('.aside-content').addClass('aside-width');
				$('.aside').width('32px');
			} else {
				$('.block').removeClass('content-icon');
				$('.aside').width('24%')
			}
		} 
		else {
			if ($('#animate-pannel').hasClass('slide-btn')) {
				$('.block').removeClass('content-icon');
				$('.slide-container').css('width', wid);
				$('.aside-content').addClass('aside-width');
				$('.aside').width('42px');
			} else {
				$('.block').removeClass('content-icon');
				$('.aside').width('24.2%')
			}
		}
		if ($('#animate-pannel').hasClass('slide-btn')) {
		} else {
		}
	}

	function objectWidth() {
		if (!$('#animate-pannel').hasClass('slide-btn')) {
			if ($(window).width() > 1024) {
				$('.objects').width('20%')
			} else if ($(window).width() == 1024) {
				$('.objects').width('25%')
			} else if ($(window).width() == 768) {
				$('.objects').width('25%');
			} else if ($(window).width() < 768) {
				$('.objects').width('100%')
			}
		} else {
			if ($(window).width() > 1024) {
				$('.objects').width('25%')
			} else if ($(window).width() == 1024) {
				$('.objects').width('33.3%');
			} else if ($(window).width() == 768) {
				$('.objects').width('33.3%')
			} else if ($(window).width() < 768) {
				$('.objects').width('100%')
			}
		}
	}

	function objectWidthResize() {
		if ($('#animate-pannel').hasClass('slide-btn')) {
			if ($(window).width() > 1024) {
				$('.objects').width('20%')
			} else if ($(window).width() == 1024) {
				$('.objects').width('25%')
			} else if ($(window).width() == 768) {
				$('.objects').width('25%');
			}
		} else {
			if ($(window).width() > 1024) {
				$('.objects').width('25%')
			} else if ($(window).width() == 1024) {
				$('.objects').width('33.3%');
			} else if ($(window).width() == 768) {
				$('.objects').width('33.3%')
			}
		}
	}
	if ($('.banner-section .bxslider').length > 0) {
			$('.bxslider').bxSlider({
				auto: true,
				speed:3000
			});
		}
		else{
			$('.bxslider').bxSlider({
				auto: true,
				speed:3000,
				infiniteLoop:false
			});
		}

})(jQuery)