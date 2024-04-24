document.getElementById('welcomeText').addEventListener('click', function() {
    var colors = ['#262120', '#ab6db6', '#f0e8c5', '#f5f5f5', '#ffffff'];
    var randomColor = colors[Math.floor(Math.random() * colors.length)];
    document.body.style.backgroundColor = randomColor;
});
