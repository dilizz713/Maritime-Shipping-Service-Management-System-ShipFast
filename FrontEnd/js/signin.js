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
                let msg = xhr.responseJSON ? xhr.responseJSON.message : "Login failed";
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
                let msg = xhr.responseJSON ? xhr.responseJSON.message : "Signup failed";
                alert(msg);
            }
        });
    });
});
