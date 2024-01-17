let inputs = document.querySelectorAll("input");
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
    document.getElementById("role").disabled = disabled;
    document.getElementById("isLocked").disabled = disabled;
}

function setFormFields(profileObject)
{
    document.getElementById("name").value = profileObject.name;
    document.getElementById("email").value = profileObject.email;
    document.getElementById("primaryPhone").value = profileObject.primaryPhone;
    document.getElementById("secondaryPhone").value = profileObject.secondaryPhone;
    document.getElementById("address").value = profileObject.address;
    document.getElementById("role").value = profileObject.role;
    document.getElementById("isLocked").checked = profileObject.isLocked;
}

function getProfileObject()
{
    let profileObject = {};

    profileObject.name = document.getElementById("name").value;
    profileObject.email = document.getElementById("email").value;
    profileObject.primaryPhone = document.getElementById("primaryPhone").value;
    profileObject.secondaryPhone = document.getElementById("secondaryPhone").value;
    profileObject.address = document.getElementById("address").value;
    profileObject.role = document.getElementById("role").value;
    profileObject.isLocked = document.getElementById("isLocked").checked;

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
    const id = document.getElementById("id").innerText;
    const url= `/api/manager/getUser/${id}/update`;
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

async function deleteUser()
{
    const id = document.getElementById("id").innerText;
    const name = document.getElementById("name").value;

    let isConfirmed= confirm(`Are you sure you want to delete the user id:${id} name:${name}?`);

    if (!isConfirmed) return;

    const url = `/api/manager/getUser/${id}/delete`;

    const response= await fetch(url, {method: 'DELETE'});

    if (response.ok)
    {
        alert('The user was deleted successfully');
        history.back();
    }
    else
    {
        alert('There was an error while trying to delete the user');
    }
}

