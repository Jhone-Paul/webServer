document.getElementById('welcomeText').addEventListener('click', function() {
    var colors = ['#2E122D', '#530332', '#800834', '#1F313B', '#EE4540'];
    var randomColor = colors[Math.floor(Math.random() * colors.length)];
    document.body.style.backgroundColor = randomColor;
});
