async function register()
{
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const name = document.getElementById("name").value;
    const primaryPhone = document.getElementById("primaryPhone").value;
    const secondaryPhone = document.getElementById("secondaryPhone").value;
    const address = document.getElementById("address").value;

    const registrationRequest= {
        'email': email,
        'password': password,
        'name': name,
        'primaryPhone': primaryPhone,
        'secondaryPhone': secondaryPhone,
        'address': address,
    };

    // sendRequest(registrationRequest)
    //     .then(r=> handle(r))
    //     .catch(e => failure(e));

    startAnimation();
    const url = 'http://localhost:8080/auth/register';
    const response= await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registrationRequest)
    });

    if (!response.ok)
    {
        endAnimation();
        modal("Registration failed", "The registration process has failed, try again.");
        return;
    }

    const modalResponse = await response.json();

    endAnimation();

    // let modalButton= document.querySelector('#modal-button');
    //
    // modalButton.innerText = 'Go to login';
    //
    // modalButton.addEventListener('click', ()=>
    // {
    //     window.location.href = '/auth/login';
    // });

    modal(modalResponse.title, modalResponse.description);
}

async function sendRequest(registrationRequest)
{
    startAnimation();
    const url = 'http://localhost:8080/auth/register';
    return await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registrationRequest)
    });
}

function handle(r)
{
    if (r.ok)
    {
        alert("Registration successful, redirecting to login");
        window.location.href = domain + "/auth/login";
    }
    else
    {
        endAnimation();
        modal("Registration failed", "The registration process has failed, try again.");
    }
}

function failure(e)
{
    endAnimation();
    modal("Registration failed", "The registration process has failed, try again.");
}

function startAnimation()
{
    let button = document.getElementById("form-button");
    button.disabled = true;
    button.backgroundColor = "black";
    button.textContent = "";
    let div = document.createElement("div");
    div.className = "material-symbols-outlined rotation";
    div.textContent = "progress_activity";
    button.appendChild(div);
}

function endAnimation()
{
    let button = document.getElementById("form-button");
    button.disabled = false;
    button.innerHTML = "Register";
}
