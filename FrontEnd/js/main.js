
$(document).ready(function () {
    const username = localStorage.getItem("username");

    if (username) {
        // User is signed in
        $("#navbarUsername").text(username);
        $("#userNav").show();
        $("#signinNav").hide();
        $("#logout-btn").show();

    } else {
        // User not signed in
        $("#userNav").hide();
        $("#signinNav").show();


    }

    if (!localStorage.getItem("username")) {
        setTimeout(function() {
            var toastEl = $('#signinToast');
            var toast = new bootstrap.Toast(toastEl);
            toast.show();
        }, 5000); // 5-second delay
    }

    // -------------------- Reveal Elements on Scroll --------------------
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

    // -------------------- Animate Count --------------------
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

// Logout function
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




