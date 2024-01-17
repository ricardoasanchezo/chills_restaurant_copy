window.onload = ()=> getUserList();

const userTableBody = document.querySelector('#user-table-body');
let userList;

async function getUserList()
{
    const url = '/api/user/getAllUsers';
    const response = await fetch(url);

    if (!response.ok)
        alert('There was an error fetching the user list');

    userList = await response.json();

    displayUsers(userList);
}

function searchUsers(searchTerm)
{
    const searchResults = userList.filter(user =>
        user.id.toString().includes(searchTerm) ||
        user.email.toLowerCase().includes(searchTerm) ||
        user.name.toLowerCase().includes(searchTerm) ||
        user.role.toLowerCase().includes(searchTerm)
    );

    if (searchResults.length === 0)
    {
        userTableBody.innerHTML = '<tr><td id="no-results" colspan=5>No results found</td></tr>';
        return;
    }

    displayUsers(searchResults);
}

function displayUsers(userList)
{
    userTableBody.innerHTML = '';

    userList.forEach(userDto =>
    {
        let row = document.createElement('tr');

        row.innerHTML = `
            <tr>
                <td>${userDto.id}</td>
                <td>${userDto.name}</td>
                <td>${userDto.email}</td>
                <td>${userDto.role}</td>
                <td>${userDto.isLocked ? 'Yes' : 'No'}</td>
            </tr>
        `;

        row.addEventListener('click', () =>
        {
            window.location.href = `/view-users/${userDto.id}`;
        });

        userTableBody.appendChild(row);
    });
}
