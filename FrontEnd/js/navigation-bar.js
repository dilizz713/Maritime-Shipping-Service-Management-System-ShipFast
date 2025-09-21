$(document).ready(function () {
    const username = localStorage.getItem("username");
    const role = localStorage.getItem("role");

    if (username && role) {
        $("#user-info").text(`${username} (${role})`);
    } else {
        window.location.href = "../html/signin.html";
    }

    if (role === "EMPLOYEE") {
        $(".user-btn").hide();
        $(".costing-btn").hide();
    }


});


$("#logout").on("click", function (e) {
    e.preventDefault();

    $.ajax({
        url: "http://localhost:8080/api/v1/auth/logout",
        type: "POST",
        xhrFields: { withCredentials: true },
        success: function () {
            localStorage.clear();
            window.location.replace("../html/signin.html");
        },
        error: function () {
            alert("Logout failed. Please try again.");
        }
    });
});


