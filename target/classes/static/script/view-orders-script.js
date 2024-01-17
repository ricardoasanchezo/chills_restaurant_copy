window.onload = ()=> getUserList();

const userTableBody = document.querySelector('#orders-table-body');
let orderList;

async function getUserList()
{
    const url = '/api/orders/getAll';
    const response = await fetch(url);

    if (!response.ok)
        alert('There was an error fetching the user list');

    orderList = await response.json();

    displayOrders(orderList);
}

function searchOrders(searchTerm)
{
    const searchResults = orderList.filter(order =>
        order.id.toString().includes(searchTerm) ||
        (order.orderCreatorEmail && order.orderCreatorEmail.toLowerCase().includes(searchTerm)) ||
        (order.orderOwnerEmail && order.orderOwnerEmail.toLowerCase().includes(searchTerm)) ||
        order.orderType.toLowerCase().includes(searchTerm) ||
        order.orderState.toLowerCase().includes(searchTerm)
    );

    if (searchResults.length === 0)
    {
        userTableBody.innerHTML = '<tr><td id="no-results" colspan=6>No results found</td></tr>';
        return;
    }

    displayOrders(searchResults);
}


function displayOrders(orderList)
{
    userTableBody.innerHTML = '';

    orderList.forEach(orderDto =>
    {
        if (orderDto.orderCreatorEmail === null)
            orderDto.orderCreatorEmail = 'No creator';

        if (orderDto.orderOwnerEmail === null)
            orderDto.orderOwnerEmail = 'No owner';

        let row = document.createElement('tr');

        let date = new Date(orderDto.createdAt);

        row.innerHTML = `
            <tr>
                <td>${orderDto.id}</td>
                <td>${orderDto.orderCreatorEmail}</td>
                <td>${orderDto.orderOwnerEmail}</td>
                <td>${date.toDateString()} ${date.toTimeString().substring(0,8)}</td>
                <td>${orderDto.orderType}</td>
                <td>${orderDto.orderState}</td>
            </tr>
        `;

        // row.addEventListener('click', () =>
        // {
        //     window.location.href = `/manager/view-users/${orderDto.id}`;
        // });

        userTableBody.appendChild(row);
    });
}
