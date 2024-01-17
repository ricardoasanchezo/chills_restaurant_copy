let orderTicketContainer= document.querySelector('#order-container');
let closedOrderTicketContainer= document.querySelector('#closed-order-container');

let orderTicketMap= new Map();
let newestOrderDate= new Date(0);

window.onload = ()=> getInitialOrderList();

async function getInitialOrderList()
{
    startLoadingAnimation(orderTicketContainer);

    const response = await fetch(`${domain}/api/orders/getAll`);

    stopLoadingAnimation();

    if (!response.ok)
        throw new Error(`HTTP error! Status: ${response.status}`);

    const orderList = await response.json();

    addOrderTickets(orderList);

    setInterval(()=> {
        getAllOrdersAfterLastOrder();
        moveClosedOrders();
    }, 10000);
}

async function getAllOrdersAfterLastOrder()
{
    console.log('Getting all orders after newest order. Last order time: ' + newestOrderDate);

    let unalteredISODate = getUnalteredISODateString(newestOrderDate);

    console.log(unalteredISODate);

    let url = `${domain}/api/orders/getAllAfter?isoDateString=${unalteredISODate}`;

    const response = await fetch(url);

    if (!response.ok)
        throw new Error(`HTTP error! Status: ${response.status}`);

    const orderList = await response.json();

    console.log(orderList);

    addOrderTickets(orderList);
}

function addOrderTickets(orderList)
{
    if (orderList.length === 0)
        return;

    for (let i = 0; i < orderList.length; i++)
    {
        let order = orderList[i];

        if (orderTicketMap.has(order.id))
            continue;

        let orderTicket= new OrderTicket(order);

        orderTicketMap.set(orderTicket.orderId, orderTicket);

        newestOrderDate = (orderTicket.createdAt.getTime() > newestOrderDate.getTime()) ? orderTicket.createdAt : newestOrderDate;

        if (orderTicket.closedAt === null)
            orderTicketContainer.prepend(orderTicket);
        else
            closedOrderTicketContainer.prepend(orderTicket);
    }
}

function moveClosedOrders()
{
    let orderTicketList = orderTicketContainer.children;
    for (let i = 0; i < orderTicketList.length; i++)
    {
        let orderTicket = orderTicketList[i];
        if (orderTicket.closedAt !== null)
            closedOrderTicketContainer.prepend(orderTicket);
    }
}

function getUnalteredISODateString(date)
{
    let dateCopy = new Date(date.getTime());

    let offsetHours= dateCopy.getTimezoneOffset() / 60;
    dateCopy.setHours(dateCopy.getHours() - offsetHours);
    return dateCopy.toISOString();
}

function toggleHideContainer(button, containerId)
{
    button.innerText = (button.innerText === 'Show') ? 'Hide' : 'Show';
    let element = document.getElementById(containerId);

    if (element.classList.contains('display-none'))
        element.classList.remove('display-none')
    else
        element.classList.add('display-none');
}
