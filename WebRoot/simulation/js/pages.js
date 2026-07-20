$(function() {
					var oNum = parseInt($(".e-p-tite").text());
					$(".e-prev").on("click", function() {
						if(oNum - 1 <= 0) {
							alert("这是第一题！")
							return;
						} else {
							$(".e-subject-container").load("subject." + (oNum - 1) + ".html");
						}

					});
					$(".e-next").on("click", function() {
						$(".e-operate-container").load("subject." + (oNum + 1) + ".html");
					})
				})