
$(document).ready(function () {


    $("#show-signup").click(function () {
        $("#signin-form").removeClass("active");
        $("#signup-form").addClass("active");
    });

    $("#show-signin").click(function () {
        $("#signup-form").removeClass("active");
        $("#signin-form").addClass("active");
    });

    // ------------------- SIGN IN -------------------
    $("#signin-form").submit(function (e) {
        e.preventDefault();

        const username = $("#username").val();
        const password = $("#password").val();

        $.ajax({
            url: "http://localhost:8080/api/v1/auth/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ username, password }),
            xhrFields: { withCredentials: true },
            success: function (res) {
                const role = res.data.role;
                const userName = res.data.userName;

                alert(`Welcome ${userName} (${role})`);


                localStorage.setItem("username", userName);


                if (role === "ADMIN" || role === "EMPLOYEE") {
                    window.location.href = "../html/dashboard.html";
                } else if (role === "CUSTOMER") {
                    window.location.href = "../index.html";
                } else {
                    alert("Unauthorized role! Access denied.");
                    $("#signin-form")[0].reset();
                }
            },
            error: function (xhr) {
                let msg = "Login failed. Please try again.";

                if (xhr.status === 404) {
                    msg = "User not found. Please sign up first.";
                    $("#signin-form")[0].reset();
                } else if (xhr.responseJSON && xhr.responseJSON.message) {
                    msg = xhr.responseJSON.message;
                } else if (xhr.status === 500) {
                    msg = "Server error, please try later.";
                }

                alert(msg);
            }
        });
    });

    // ------------------- SIGN UP -------------------
    $("#signup-form").submit(function (e) {
        e.preventDefault();

        const name = $("#name").val();
        const username = $("#user-name").val();
        const password = $("#pw").val();

        $.ajax({
            url: "http://localhost:8080/api/v1/auth/signup",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ name, username, password }),
            success: function (res) {
                alert(res.message || "Signup successful! Please login.");
                $("#signup-form")[0].reset();
                $("#show-signin").click();
            },
            error: function (xhr) {
                let msg = "Signup failed. Please try again.";
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    msg = xhr.responseJSON.message;
                } else if (xhr.status === 409) {
                    msg = "This user already exists!";
                } else if (xhr.status === 500) {
                    msg = "Server error, please try later.";
                }
                alert(msg);
            }
        });
    });
});




