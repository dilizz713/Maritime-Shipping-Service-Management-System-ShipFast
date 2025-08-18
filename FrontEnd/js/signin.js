const signinForm = document.getElementById('signin-form');
const signupForm = document.getElementById('signup-form');
const showSignup = document.getElementById('show-signup');
const showSignin = document.getElementById('show-signin');

showSignup.addEventListener('click', () => {
    signinForm.classList.remove('active');
    signupForm.classList.add('active');
});

showSignin.addEventListener('click', () => {
    signupForm.classList.remove('active');
    signinForm.classList.add('active');
});


signinForm.addEventListener('submit', (e) => {
    e.preventDefault();
    alert('Signed in successfully!');
});

signupForm.addEventListener('submit', (e) => {
    e.preventDefault();
    alert('Account created successfully!');
});
