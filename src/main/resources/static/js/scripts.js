document.querySelector('form').addEventListener('submit', function() {
    this.querySelector('button').innerText = 'Shortening...';
});

function copyToClipboard() {
    let shortUrl = document.getElementById('shortUrl');
    let textarea = document.createElement('textarea');
    textarea.value = shortUrl.href;
    document.body.appendChild(textarea);
    textarea.select();
    document.execCommand('copy');
    document.body.removeChild(textarea);
    alert("Shortened URL copied to clipboard.");
}