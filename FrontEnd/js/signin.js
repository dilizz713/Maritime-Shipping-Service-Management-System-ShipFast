$(document).ready(function () {

    $("#show-signup").click(function () {
        $("#signin-form").removeClass("active");
        $("#signup-form").addClass("active");
    });

    $("#show-signin").click(function () {
        $("#signup-form").removeClass("active");
        $("#signin-form").addClass("active");
    });

    // ----------------------------------------- SIGN IN ----------------------------------
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
                alert(`Welcome ${res.data.userName} (${res.data.role})`);
                window.location.href = "../index.html";
            },
            error: function (xhr) {
                let msg = "Login failed. Please try again.";

                if (xhr.status === 404) {
                    msg = "User not found. Please signup first.";

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

    // ----------------------------------------- SIGN UP ----------------------------------
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
