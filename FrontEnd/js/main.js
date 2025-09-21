
$(document).ready(function () {
    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role");

    if (username) {
        $("#navbarUsername").text(username);
        $("#signinNav").hide();
        $("#logout-btn").show();

        if (role === "CUSTOMER") {
            $("#userNav").show();
        } else if (role === "ADMIN" || role === "EMPLOYEE") {
            $("#userNav").hide();

            if ($("#systemNav").length === 0) {
                $(".navbar-nav").append(`
                    <li class="nav-item ms-2" id="systemNav">
                        <a href="html/admin-dashboard.html" class="btn btn-shipfast px-3 d-flex align-items-center gap-2">
                            <i class="bi bi-speedometer2 fs-5"></i> System
                        </a>
                    </li>
                `);
            }

        }
    } else {
        $("#userNav").hide();
        $("#signinNav").show();
        $("#logout-btn").hide();
    }


    if (!username) {
        setTimeout(function() {
            var toastEl = $('#signinToast');
            var toast = new bootstrap.Toast(toastEl);
            toast.show();
        }, 5000);
    }


    const $reveals = $('.reveal');
    const io = new IntersectionObserver(entries => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                $(entry.target).addClass('show');
                io.unobserve(entry.target);
            }
        });
    }, { threshold: 0.15 });

    $reveals.each(function () {
        io.observe(this);
    });

    // Animated counters
    function animateCount($el, end, duration = 1200) {
        const start = 0;
        const startTime = performance.now();

        function tick(now) {
            const progress = Math.min((now - startTime) / duration, 1);
            $el.text(Math.floor(start + (end - start) * progress).toLocaleString());
            if (progress < 1) requestAnimationFrame(tick);
        }

        requestAnimationFrame(tick);
    }

    $('[data-count]').each(function () {
        const $el = $(this);
        const observer = new IntersectionObserver(([entry]) => {
            if (entry.isIntersecting) {
                animateCount($el, parseInt($el.data('count'), 10));
                observer.disconnect();
            }
        }, { threshold: 0.6 });
        observer.observe(this);
    });

    $('#year').text(new Date().getFullYear());

    // Quotation
    $(".quotation-form").on("submit", function (e) {
        e.preventDefault();

        const requestData = {
            companyName: $("#companyName").val(),
            email: $("#email").val(),
            harbour: $("#harbour").val(),
            position: $("#position").val(),
            service: $("#service").val(),
            message: $("#message").val()
        };

        $.ajax({
            url: "http://localhost:8080/api/v1/quotation/request",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestData),
            success: function (res) {
                showToast("success", res.message);
                $(".quotation-form")[0].reset();
            },
            error: function (xhr) {
                const errMsg = xhr.responseJSON?.message || "Failed to send request. Try again later.";
                showToast("danger", errMsg);
            }
        });
    });

// Helper function to show Bootstrap Toast
    function showToast(type, message) {
        const toastHTML = `
        <div class="toast align-items-center text-bg-${type} border-0 show" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">${message}</div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `;
        const $toastContainer = $("#toastContainer");
        if ($toastContainer.length === 0) {
            $("body").append('<div id="toastContainer" class="position-fixed top-0 end-0 p-3" style="z-index: 1050;"></div>');
        }
        $("#toastContainer").append(toastHTML);
        setTimeout(() => $(".toast").first().remove(), 5000); // auto hide after 5s
    }
});


// Logout
function logoutUser() {
    if (confirm("Are you sure you want to log out?")) {
        $.ajax({
            url: "http://localhost:8080/api/v1/auth/logout",
            type: "POST",
            xhrFields: {withCredentials: true},
            success: function () {
                localStorage.clear();
                window.location.href = "index.html";
            },
            error: function () {
                alert("Logout failed. Please try again.");
            }
        });
    }
}






