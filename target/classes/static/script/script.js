const domain = "http://localhost:8080";

function modal(title, message)
{
    document.getElementById("modal-title").innerText = title;
    document.getElementById("modal-message").innerText = message;

    document.getElementById("modal-button").onclick = () =>
    {
        document.getElementById("modal").style.display = "none";
    }

    document.getElementById("modal").style.display = "block";
}

function startLoadingAnimation(container)
{
    const spinner = document.createElement('div');
    spinner.className = 'spinner';
    container.appendChild(spinner);
}

function stopLoadingAnimation()
{
    const spinner = document.querySelector('.spinner');
    if (spinner)
        spinner.parentNode.removeChild(spinner);
}
