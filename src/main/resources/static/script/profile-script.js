let savedProfileObject = {};
let editButton = document.getElementById("edit-button");
let saveButton = document.getElementById("save-button");
let cancelButton = document.getElementById("cancel-button");


function setFormDisabled(disabled)
{
    document.getElementById("name").disabled = disabled;
    document.getElementById("email").disabled = disabled;
    document.getElementById("primaryPhone").disabled = disabled;
    document.getElementById("secondaryPhone").disabled = disabled;
    document.getElementById("address").disabled = disabled;
}

function setFormFields(profileObject)
{
    document.getElementById("name").value = profileObject.name;
    document.getElementById("email").value = profileObject.email;
    document.getElementById("primaryPhone").value = profileObject.primaryPhone;
    document.getElementById("secondaryPhone").value = profileObject.secondaryPhone;
    document.getElementById("address").value = profileObject.address;
}

function getProfileObject()
{
    let profileObject = {};

    profileObject.name = document.getElementById("name").value;
    profileObject.email = document.getElementById("email").value;
    profileObject.primaryPhone = document.getElementById("primaryPhone").value;
    profileObject.secondaryPhone = document.getElementById("secondaryPhone").value;
    profileObject.address = document.getElementById("address").value;

    console.log(profileObject);

    return profileObject;
}

function editProfile()
{
    editButton.style.display = "none";
    saveButton.style.display = "inline-block";
    cancelButton.style.display = "inline-block";

    savedProfileObject = getProfileObject();

    setFormDisabled(false);
}

function cancelEdit()
{
    editButton.style.display = "inline-block";
    saveButton.style.display = "none";
    cancelButton.style.display = "none";

    setFormFields(savedProfileObject);
    setFormDisabled(true);

    // document.getElementById("isLocked").checked = savedProfileObject.isLocked;
}

function saveChanges()
{
    let profileUpdateRequest = getProfileObject();

    sendRequest(profileUpdateRequest)
        .then(r => handle(r))
        .catch(e => failure(e));
}

async function sendRequest(request)
{
    const url= `${domain}/api/user/updateLoggedInUser`;
    return await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(request)
    });
}

function handle(r)
{
    if (r.ok)
    {
        alert("Profile updated successfully");
        console.log(r.json());
        location.reload();
    }
    else
    {
        alert("Profile update failed");
    }
}

function failure(e)
{
    alert("Profile update failed");
}

async function deleteMyAccount()
{
    let isConfirmed= confirm(`Are you sure you want to delete your account?`);

    if (!isConfirmed) return;

    const url = `/api/user/deleteMyAccount`;

    const response= await fetch(url, {method: 'DELETE'});

    if (response.ok)
    {
        alert('The user was deleted successfully');
        location.reload();
    }
    else
    {
        alert('There was an error while trying to delete the user');
    }
}
