
$(document).ready(function () {
    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role"); // get role

    if (username) {
        // Show username & common signed-in UI
        $("#navbarUsername").text(username);
        $("#signinNav").hide();
        $("#logout-btn").show();

        if (role === "CUSTOMER") {
            // Customers see myShip
            $("#userNav").show();
        } else if (role === "ADMIN" || role === "EMPLOYEE") {
            // Hide myShip
            $("#userNav").hide();

            // Add ShipFast System button if not already present
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
        // Not signed in
        $("#userNav").hide();
        $("#signinNav").show();
        $("#logout-btn").hide();
    }

    // Show reminder toast if not signed in
    if (!username) {
        setTimeout(function() {
            var toastEl = $('#signinToast');
            var toast = new bootstrap.Toast(toastEl);
            toast.show();
        }, 5000);
    }

    // Other animations...
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




