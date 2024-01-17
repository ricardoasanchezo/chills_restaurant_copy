// async function createOrder()
// {
//     const btnPay = document.getElementById('btnPayOrder');
//     btnPay.disabled = true;
//     btnPay.style.opacity = '0.6';
//     btnPay.style.cursor = 'not-allowed';
//     const customerComments = document.getElementById("customerComments").value;
//     const orderType = document.getElementById("orderType").value;
//
//     const addToOrder =
//         {
//             'itemIds': orderItemsID,
//             'customerComments': customerComments,
//             'orderType': orderType,
//         }
//
//     const response = await fetch("http://localhost:8080/api/orders/create", {
//         method: "POST",
//         headers: {'Content-Type': 'application/json'},
//         body: JSON.stringify(addToOrder),
//     });
//
//     const order = await response.json();
//     console.log(order);
// }
//
// async function itemsOnMenu()
// {
//     const div = document.getElementById('itemsOnMenu');
//
//     if(div != null)
//     {
//         const response = await fetch(domain + '/api/item/getAll');
//
//         if (!response.ok)
//             throw new Error('There was an error.');
//
//         const data = await response.json();
//
//         for (let i = 0; i < data.length; i++)
//         {
//             const separator = document.createElement('br');
//             const checkbox = document.createElement('input');
//             checkbox.type = "checkbox";
//             checkbox.name = "items";
//             checkbox.value = data[i].id;
//             checkbox.id = data[i].name;
//             const label = document.createElement('label');
//             label.htmlFor = data[i].name;
//             label.appendChild(document.createTextNode(data[i].name));
//
//             div.appendChild(checkbox);
//             div.appendChild(label);
//             div.appendChild(separator);
//
//             if (data[i].isOnMenu === true)
//             {
//                 document.getElementById(data[i].name).checked = true;
//             }
//
//             label.style.display = "inline";
//         }
//     }
// }

let customerEmail = "";

const url = "/api/item/getAllItemsOnMenu";
const itemsOnMenu = [];
const idItemsOnMenu = [];
const itemIDs =[];

let itemID = 0;
let orderItemsID = [];
let orderItemsNames = [];
let itemPrice = [];
let subtotal = 0;
const tax = 0.07;
let taxes = 0;
let totalPrice = 0;

let userName;
let userEmail;
let userPhone;
let userAddress;

const itemNames =[];

// Load menu function
async function getMenu()
{
    const response = await fetch(domain + url);

    if (!response.ok)
        throw new Error('There was an error.');

    const data = await response.json();

    let itemPriceFormat = new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
    });

    let itemAppetizers = data.filter(data => data.category.name === "Appetizers");
    let itemSandwiches = data.filter(data => data.category.name === "Sandwiches");
    let itemSalads = data.filter(data => data.category.name === "Salads");
    let itemSides = data.filter(data => data.category.name === "Sides");
    let itemDrinks = data.filter(data => data.category.name === "Drinks");
    let itemDesserts = data.filter(data => data.category.name === "Desserts");

    const dataAppetizers = itemAppetizers.map(item => {
        item.price = (item.price/ 100).toFixed(2)
        itemsOnMenu.push(item.name)
        idItemsOnMenu.push(item.id)
        return `<div class="menu-item item-appetizers">
                            <img class="item-picture" src="${item.picture}" alt="${item.name}"><br>
                            <b>${item.name}</b>
                            <div class="tooltip">
                                <button class="btnAddToOrder" onclick="itemIDs.push(${item.id});
                                                                        itemID = ${item.id};
                                                                        itemNames.push('${item.name}');
                                                                        addItemToOrder();">
                                    <b>&plus;</b>
                                </button>
                            <span class="tooltiptext">Add to Order</span>
                            </div>
                            <br>
                            ${item.description}<br>
                            ${itemPriceFormat.format(item.price)}<br>
                           Category: ${item.category.name}<br>
                           Tags<br>
                        </div>`
    })
        .join("");
    let appetizerTags = itemAppetizers.reduce((prev, next) => prev.concat(next.tags), []);
    const appetizerTag = appetizerTags.map(itemTag => {
        return `<div class="menu-item-tags tags-appetizers">
                           | <i>${itemTag.name}</i> |
                        </div>`;
    })
        .join("")
    const dataSandwiches = itemSandwiches.map(item => {
        item.price = (item.price/ 100).toFixed(2)
        itemsOnMenu.push(item.name)
        idItemsOnMenu.push(item.id)
        return `<div class="menu-item item-sandwiches">
                            <img class="item-picture" src="${item.picture}" alt="${item.name}"><br>
                            <b>${item.name}</b>
                            <div class="tooltip">
                                <button class="btnAddToOrder" onclick="itemIDs.push(${item.id});
                                                                        itemID = ${item.id};
                                                                        itemNames.push('${item.name}');
                                                                        addItemToOrder();">
                                    <b>&plus;</b>
                                </button>
                            <span class="tooltiptext">Add to Order</span>
                            </div>
                            <br>
                            ${item.description}<br>
                            ${itemPriceFormat.format(item.price)}<br>
                           Category: ${item.category.name}<br>
                           Tags<br>
                        </div>`
    })
        .join("");
    let sandwichesTags = itemSandwiches.reduce((prev, next) => prev.concat(next.tags), []);
    const sandwichesTag = sandwichesTags.map(itemTag => {
        return `<div class="menu-item-tags tags-sandwiches">
                           | <i>${itemTag.name}</i> |
                        </div>`;
    })
        .join("");

    const dataSalads = itemSalads.map(item => {
        item.price = (item.price/ 100).toFixed(2)
        itemsOnMenu.push(item.name)
        idItemsOnMenu.push(item.id)
        return `<div class="menu-item item-salads">
                            <img class="item-picture" src="${item.picture}" alt="${item.name}"><br>
                            <b>${item.name}</b>
                            <div class="tooltip">
                                <button class="btnAddToOrder" onclick="itemIDs.push(${item.id});
                                                                        itemID = ${item.id};
                                                                        itemNames.push('${item.name}');
                                                                        addItemToOrder();">
                                    <b>&plus;</b>
                                </button>
                            <span class="tooltiptext">Add to Order</span>
                            </div>
                            <br>
                            ${item.description}<br>
                            ${itemPriceFormat.format(item.price)}<br>
                           Category: ${item.category.name}<br>
                           Tags<br>
                        </div>`
    })
        .join("");
    let saladTags = itemSalads.reduce((prev, next) => prev.concat(next.tags), []);
    const saladTag = saladTags.map(itemTag => {
        return `<div class="menu-item-tags tags-salads">
                           | <i>${itemTag.name}</i> |
                        </div>`
    })
        .join("");

    const dataSides = itemSides.map(item => {
        item.price = (item.price/ 100).toFixed(2)
        itemsOnMenu.push(item.name)
        idItemsOnMenu.push(item.id)
        return `<div class="menu-item item-sides">
                            <img class="item-picture" src="${item.picture}" alt="${item.name}"><br>
                            <b>${item.name}</b>
                            <div class="tooltip">
                                <button class="btnAddToOrder" onclick="itemIDs.push(${item.id});
                                                                        itemID = ${item.id};
                                                                        itemNames.push('${item.name}');
                                                                        addItemToOrder();">
                                    <b>&plus;</b>
                                </button>
                            <span class="tooltiptext">Add to Order</span>
                            </div>
                            <br>
                            ${item.description}<br>
                            ${itemPriceFormat.format(item.price)}<br>
                            Category: ${item.category.name}<br>
                            Tags<br>
                        </div>`
    })
        .join("");
    let sidesTags = itemSides.reduce((prev, next) => prev.concat(next.tags), []);
    const sidesTag = sidesTags.map(itemTag => {
        return `<div class="menu-item-tags tags-sides">
                           | <i>${itemTag.name}</i> |
                        </div>`
    })
        .join("");

    const dataDrinks = itemDrinks.map(item => {
        item.price = (item.price/ 100).toFixed(2)
        itemsOnMenu.push(item.name)
        idItemsOnMenu.push(item.id)
        return `<div class="menu-item item-drinks">
                            <img class="item-picture" src="${item.picture}" alt="${item.name}"><br>
                            <b>${item.name}</b>
                            <div class="tooltip">
                                <button class="btnAddToOrder" onclick="itemIDs.push(${item.id});
                                                                        itemID = ${item.id};
                                                                        itemNames.push('${item.name}');
                                                                        addItemToOrder();">
                                    <b>&plus;</b>
                                </button>
                            <span class="tooltiptext">Add to Order</span>
                            </div>
                            <br>
                            ${item.description}<br>
                            ${itemPriceFormat.format(item.price)}<br>
                           Category: ${item.category.name}<br>
                           Tags<br>
                        </div>`
    })
        .join("");
    let drinkTags = itemDrinks.reduce((prev, next) => prev.concat(next.tags), []);
    const drinkTag = drinkTags.map(itemTag => {
        return `<div class="menu-item-tags tags-drinks">
                           | <i>${itemTag.name}</i> |
                        </div>`
    })
        .join("");

    const dataDesserts = itemDesserts.map(item => {
        item.price = (item.price/ 100).toFixed(2)
        itemsOnMenu.push(item.name)
        idItemsOnMenu.push(item.id)
        return `<div class="menu-item item-desserts">
                            <img class="item-picture" src="${item.picture}" alt="${item.name}"><br>
                            <b>${item.name}</b>
                            <div class="tooltip">
                                <button class="btnAddToOrder" onclick="itemIDs.push(${item.id});
                                                                        itemID = ${item.id};
                                                                        itemNames.push('${item.name}');
                                                                        addItemToOrder();">
                                    <b>&plus;</b>
                                </button>
                            <span class="tooltiptext">Add to Order</span>
                            </div>
                            <br>
                            ${item.description}<br>
                            ${itemPriceFormat.format(item.price)}<br>
                            Category: ${item.category.name}<br>
                           Tags<br>
                        </div>`
    })
        .join("");
    let dessertTags = itemDesserts.reduce((prev, next) => prev.concat(next.tags), []);
    const dessertTag = dessertTags.map(itemTag => {
        return `<div class="menu-item-tags tags-desserts">
                            | <i>${itemTag.name}</i> |
                        </div>`
    })
        .join("");

    document.querySelector('#appetizers').innerHTML = dataAppetizers + appetizerTag;
    document.querySelector('#sandwiches').innerHTML = dataSandwiches + sandwichesTag;
    document.querySelector('#salads').innerHTML = dataSalads + saladTag;
    document.querySelector('#sides').innerHTML = dataSides + sidesTag;
    document.querySelector('#drinks').innerHTML = dataDrinks + drinkTag;
    document.querySelector('#desserts').innerHTML = dataDesserts + dessertTag;

    // Join item div with tag div
    for (let i = 0; i < itemAppetizers.length; i++)
    {
        let items = document.getElementsByClassName('item-appetizers')[i];
        for (let i = 0; i < appetizerTags.length; i++)
        {
            let tags = document.getElementsByClassName('tags-appetizers')[i];
            items.append(tags)
        }
    }

    for (let i = 0; i < itemSandwiches.length; i++)
    {
        let items = document.getElementsByClassName('item-sandwiches')[i];
        for (let i = 0; i < sandwichesTags.length; i++)
        {
            let tags = document.getElementsByClassName('tags-sandwiches')[i];
            items.append(tags)
        }
    }

    for (let i = 0; i < itemSalads.length; i++)
    {
        let items = document.getElementsByClassName('item-salads')[i];
        for (let i = 0; i < saladTags.length; i++)
        {
            let tags = document.getElementsByClassName('tags-salads')[i];
            items.append(tags)
        }
    }

    for (let i = 0; i < itemSides.length; i++)
    {
        let items = document.getElementsByClassName('item-sides')[i];
        for (let i = 0; i < sidesTags.length; i++)
        {
            let tags = document.getElementsByClassName('tags-sides')[i];
            items.append(tags)
        }
    }

    for (let i = 0; i < itemDrinks.length; i++)
    {
        let items = document.getElementsByClassName('item-drinks')[i];
        for (let i = 0; i < drinkTags.length; i++)
        {
            let tags = document.getElementsByClassName('tags-drinks')[i];
            items.append(tags)
        }
    }

    for (let i = 0; i < itemDesserts.length; i++)
    {
        let items = document.getElementsByClassName('item-desserts')[i];
        for (let i = 0; i < dessertTags.length; i++)
        {
            let tags = document.getElementsByClassName('tags-desserts')[i];
            items.append(tags)
        }
    }
}

// Modal for adding items
document.addEventListener('DOMContentLoaded', function()
{
    const modal = document.getElementById("modalAddItem");
    const btn = document.getElementById("btnModalAdd");
    const span = document.getElementById("closeAdd");

    if(btn != null)
    {
        btn.onclick = function() {
            modal.style.display = "block";
        }
        span.onclick = function() {
            modal.style.display = "none";
        }
        window.addEventListener("click", function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        });

        const modal2 = document.getElementById("modalUpdateItem");
        const btn2 = document.getElementById("btnModalUpdate");
        const span2 = document.getElementById("closeUpdate");

        btn2.onclick = function() {
            modal2.style.display = "block";
        }
        span2.onclick = function() {
            modal2.style.display = "none";
        }
        window.addEventListener("click", function(event) {
            if (event.target === modal2) {
                modal2.style.display = "none";
            }
        });

        const modal3 = document.getElementById("modalRemoveItem");
        const btn3 = document.getElementById("btnModalDelete");
        const span3 = document.getElementById("closeDelete");

        btn3.onclick = function() {
            modal3.style.display = "block";
        }
        span3.onclick = function() {
            modal3.style.display = "none";
        }
        window.addEventListener("click", function(event) {
            if (event.target === modal3) {
                modal3.style.display = "none";
            }
        });

        //Load options for Update Modal
        setTimeout(function() {
            for (let i = 0; i < itemsOnMenu.length; i++){
                let newOption = new Option(itemsOnMenu[i], idItemsOnMenu[i]);
                const select = document.getElementById('items');
                select.add(newOption,undefined);
            }
        }, 5000);

        //Load options for Remove Modal
        setTimeout(async function() {
            const response = await fetch(domain + '/api/item/getAll');

            if (!response.ok)
                throw new Error('There was an error.');

            const data = await response.json();

            for(let i = 0; i < data.length; i++){
                let newOption = new Option(data[i].name, data[i].id);
                const select = document.getElementById('allItems');
                select.add(newOption,undefined);
            }
        }, 5000);
    }

    const modalPayment = document.getElementById("modalPayment");
    const btnPayment = document.getElementById("btnPlaceOrder");
    const spanClosePayment = document.getElementById("closePayment");
    btnPayment.disabled = true;

    btnPayment.onclick = function() {
        customerEmail = prompt('Enter the user email:');
        paymentInfo();
        modalPayment.style.display = "block";
    }
    spanClosePayment.onclick = function() {
        modalPayment.style.display = "none";
    }
    window.addEventListener("click", function(event) {
        if (event.target === modalPayment) {
            modalPayment.style.display = "none";
        }
    });
});

// Function for adding items to order
async function addItemToOrder()
{
    const orderSummaryDiv = document.createElement('div');
    const itemSummary = document.createElement('p');
    const separatorProduct = document.createElement('br');
    const divProduct = document.getElementById('product');

    //const targetNode = document.getElementById("itemsOnOrder");

    const div4 = document.getElementById('orderTotal');
    const div3 = document.getElementById('taxes');
    const div2 = document.getElementById('subtotal');

    const response = await fetch(domain + '/api/item/get/' + itemID);

    if (!response.ok)
        throw new Error('There was an error.');

    const data = await response.json();

    orderItemsID.push(data.id)
    orderItemsNames.push(data.name)
    itemPrice.push(data.price)

    subtotal = itemPrice.reduce((partialSum, a) => partialSum + a, 0);
    taxes = subtotal * tax;
    totalPrice = subtotal + taxes;

    try
    {
        const modal = document.getElementById("modalAddToOrder");
        const btn = document.getElementById("btnAdded");

        modal.style.display = "block";

        btn.onclick = function ()
        {
            modal.style.display = "none";
        }
        window.onclick = function (event)
        {
            if (event.target === modal)
            {
                modal.style.display = "none";
            }
        }
    }
    catch
    {
        errorModal()
    }

    const border = document.getElementById("itemsOnOrder");

    const div = document.getElementById('itemsOnOrder');
    const separator = document.createElement('br');
    const item = document.createElement('p');
    const button = document.createElement('button');
    const orderItemDiv = document.createElement('div');
    const btnPlaceOrder = document.getElementById('btnPlaceOrder');

    function addInfo()
    {
        div2.innerHTML = "Subtotal: $ " + ((subtotal/100).toFixed(2)).toString();
        div3.innerHTML = "Taxes: $ " + ((taxes/100).toFixed(2)).toString();
        div4.innerHTML = "Total: $ " + ((totalPrice/100).toFixed(2)).toString();
    }

    button.type = "button";
    button.className = "btnRemove";
    orderItemDiv.className = "orderItemDivs"
    btnPlaceOrder.disabled = false;
    button.onclick = function()
    {
        const index = orderItemsID.indexOf(data.id);
        const index2 = orderItemsNames.indexOf(data.name);
        const index3 = itemPrice.indexOf(data.price);

        if (index !== -1) {
            orderItemsID.splice(index, 1);
            orderItemsNames.splice(index2, 1)
            subtotal = subtotal - itemPrice[index3];
            taxes = subtotal * tax;
            totalPrice = subtotal + taxes;
            itemPrice.splice(index3, 1);
        }

        div.removeChild(orderItemDiv)
        orderItemDiv.removeChild(item);
        orderItemDiv.removeChild(button);
        orderItemDiv.removeChild(separator);

        divProduct.removeChild(orderSummaryDiv)
        orderSummaryDiv.removeChild(itemSummary);
        orderSummaryDiv.removeChild(separatorProduct);

        addInfo();

        if(orderItemsID.length === 0)
        {
            border.style.borderStyle = "none";
            btnPlaceOrder.disabled = true;
        }
    }

    let text = document.createTextNode('x');
    item.id = data.id;
    item.innerHTML = data.name + " ($" + (data.price/100).toFixed(2) + ")";

    orderSummaryDiv.className = "orderSummaryDiv";

    itemSummary.innerHTML = data.name + " ($" + (data.price/100).toFixed(2) + ")";

    const targetNode = document.getElementById("itemsOnOrder");

    const observer = new MutationObserver(() => {
        console.log("Items in order updated!");
    });

    observer.observe(targetNode, { subtree: true, childList: true });

    // document.getElementById("itemsOnOrder").addEventListener('DOMSubtreeModified', function(event){
    // });
    setTimeout(function(){
        button.appendChild(text);
        div.appendChild(orderItemDiv)
        orderItemDiv.appendChild(item);
        orderItemDiv.appendChild(button);
        orderItemDiv.appendChild(separator);
        border.style.borderStyle = 'solid';
        addInfo();

        divProduct.appendChild(orderSummaryDiv);
        orderSummaryDiv.appendChild(itemSummary);
        orderSummaryDiv.appendChild(separatorProduct);
    }, 1000);

    // if (localStorage.getItem('itemsInOrder') === null)
    // {
    //     localStorage.setItem('itemsInOrder', orderItems);
    //     orderItems = [];
    // }
    //
    // if(localStorage.getItem('itemsInOrder') !== null)
    // {
    //     stored = localStorage.getItem('itemsInOrder');
    //     stored = stored.split(",");
    //     const arrItems = stored.concat(orderItems);
    //     localStorage.setItem('itemsInOrder', arrItems);
    //     orderItems = [];
    // }
}

// Function to create order
async function createOrder()
{
    const btnPay = document.getElementById('btnPayOrder');
    btnPay.disabled = true;
    btnPay.style.opacity = '0.6';
    btnPay.style.cursor = 'not-allowed';
    const customerComments = document.getElementById("customerComments").value;
    const orderType = document.getElementById("orderType").value;

    const addToOrder =
        {
            'customerEmail': customerEmail,
            'itemIds': orderItemsID,
            'customerComments': customerComments,
            'orderType': orderType,
        }

    const url = `http://localhost:8080/api/orders/createForCustomer`;

    const response = await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(addToOrder),
    });

    const order = await response.json();
    console.log(order);

    let id = order.id;

    const responsePayment = await fetch(`http://localhost:8080/api/orders/pay?id=${id}&paidAmount=${totalPrice}`, {
        method: "PUT",
    });

    const orderPayment = await responsePayment.json();
    console.log(orderPayment);

    const paymentModal = document.getElementById('modalPayment');
    if (!response.ok || !responsePayment.ok)
    {
        paymentModal.style.display = "none";
        errorModal();
    }
    else
    {
        const modal = document.getElementById("modalOrder");
        const btn = document.getElementById("btnOrder");
        const div =  document.getElementById('orderInfo');

        paymentModal.style.display = "none";

        modal.style.display = "block";

        div.innerHTML = "<br> Order ID: " + order.id +
            "<br> Subtotal: $ " + ((subtotal/100).toFixed(2)).toString() +
            "<br> Taxes: $ " + ((taxes/100).toFixed(2)).toString() +
            "<br> Total: $ " + ((totalPrice/100).toFixed(2)).toString();

        btn.onclick = function() {
            modal.style.display = "none";
            location.reload();
        }
        window.onclick = function(event) {
            if (event.target === modal) {
                modal.style.display = "none";
                location.reload();
            }
        }
    }
    itemIDs.length = 0
}

// Function to add item to menu
async function addItem()
{
    const itemName = document.getElementById("itemName").value;
    const itemDescription = document.getElementById("itemDescription").value;
    const itemPrice = parseInt(document.getElementById("itemPrice").value);
    const itemCategory = document.getElementById("itemCategory").value;
    let itemTags = document.getElementById("itemTags").value;
    const itemPicture = document.getElementById("itemPicture").value;

    function tagsToArray(tagsArr)
    {
        tagsArr = itemTags.split(",");
        return tagsArr
    }

    itemTags = tagsToArray(itemTags);

    const addItemRequest =
        {
            'name': itemName,
            'description': itemDescription,
            'price': itemPrice,
            'category': itemCategory,
            'tags': itemTags,
            'picture': itemPicture,
        }

    const response = await fetch("http://localhost:8080/api/item/add", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(addItemRequest),
    });

    const item = await response.json();
    console.log(item);

    if (!response.ok)
    {
        errorModal();
    }
    else
    {
        const modalItem = document.getElementById("modalAddItem");

        modalItem.style.display = "none";

        successModal();
    }
}

function openNavOrder()
{
    document.getElementById("sidenav-order").style.width = "250px";
}

function closeNavOrder()
{
    document.getElementById("sidenav-order").style.width = "0";
}

function openNavManage()
{
    document.getElementById("sidenav-manage-menu").style.width = "250px";
}

function closeNavManage()
{
    document.getElementById("sidenav-manage-menu").style.width = "0";
}

function errorModal()
{
    const modal = document.getElementById("modalError");
    const btn = document.getElementById("btnError");

    modal.style.display = "block";

    btn.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
}

function successModal()
{
    const modal = document.getElementById("modalSuccess");
    const btn = document.getElementById("btnConfirm");

    modal.style.display = "block";

    btn.onclick = function() {
        modal.style.display = "none";
        location.reload();
    }
    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
            location.reload();
        }
    });
}

async function paymentInfo()
{
    const response = await fetch(domain + '/api/user/getLoggedInUser');

    if (!response.ok)
        throw new Error('There was an error.');

    const data = await response.json();

    userName = data.name;
    userEmail = data.email;
    userPhone = data.primaryPhone;
    userAddress = data.address;
    const orderType = document.getElementById("orderType").value;
    const delivery= document.getElementById('deliveryOption');

    if(orderType === "DELIVERY")
    {
        delivery.style.display = "block";
        document.getElementById('deliveryName').value = userName.toString();
        document.getElementById('deliveryEmail').value = userEmail.toString();
        document.getElementById('deliveryPhone').value = userPhone.toString();
        document.getElementById('deliveryAddress').value = userAddress.toString();
    }
    else
    {
        delivery.style.display = "none";
    }

    document.getElementById('name').innerHTML = userName.toString();
    document.getElementById('email').innerHTML = userEmail.toString();
    document.getElementById('phone').innerHTML = userPhone.toString();
    document.getElementById('address').innerHTML = userAddress.toString();

    document.getElementById("itemTotal").innerHTML = orderItemsID.length.toString();
    document.getElementById("orderTypeSelected").innerHTML = orderType.toString();
    document.getElementById("summarySubtotal").innerHTML = "Subtotal: $ " + ((subtotal/100).toFixed(2)).toString();
    document.getElementById("summaryTaxes").innerHTML = "Taxes: $ " + ((taxes/100).toFixed(2)).toString();
    document.getElementById("summaryTotal").innerHTML = "Total: $ " + ((totalPrice/100).toFixed(2)).toString();
}

async function getItem()
{

    const select = document.getElementById('items');
    const value = select.options[select.selectedIndex].value;

    if(value !== 'null')
    {
        const response = await fetch(domain + '/api/item/get/' + value);

        if (!response.ok)
            throw new Error('There was an error.');

        const data = await response.json();

        document.getElementById('updatedName').value = (data.name).toString();
        document.getElementById('updatedDescription').value = (data.description).toString();
        document.getElementById('updatedPrice').value = (data.price).toString();
        document.getElementById('updatedCategory').value = (data.category.name).toString();
        let tagsArr = [];
        for (let i = 0; i < data.tags.length; i++)
        {
            tagsArr.push((data.tags[i]).name);
        }
        document.getElementById('updatedTags').value = (tagsArr.toString());
        document.getElementById('updatedPicture').value = (data.picture).toString();

        // if(data.isOnMenu === true)
        // {
        //     let radiobtn = document.getElementById('available');
        //     radiobtn.checked = true;
        // }
        // else
        // {
        //     let radiobtn = document.getElementById('unavailable');
        //     radiobtn.checked = true;
        // }

        document.getElementById('updatedName').disabled = false;
        document.getElementById('updatedDescription').disabled = false;
        document.getElementById('updatedPrice').disabled = false;
        document.getElementById('updatedCategory').disabled = false;
        document.getElementById('updatedTags').disabled = false;
        document.getElementById('updatedPicture').disabled = false;
        // document.getElementById('available').disabled = false;
        // document.getElementById('unavailable').disabled = false;
    }
}

async function updateItem()
{
    const select = document.getElementById('items');
    const id = select.options[select.selectedIndex].value;
    const updatedName = document.getElementById("updatedName").value;
    const updatedDescription = document.getElementById("updatedDescription").value;
    const updatedPrice = parseInt(document.getElementById("updatedPrice").value);
    const updatedCategory = document.getElementById("updatedCategory").value;
    let updatedTags = document.getElementById("updatedTags").value;
    const updatedPicture = document.getElementById("updatedPicture").value;
    //let updatedAvailability;

    // if(document.getElementById('available').checked)
    // {
    //     updatedAvailability = document.getElementById('available').value;
    // }
    // else
    // {
    //     updatedAvailability = document.getElementById('unavailable').value;
    // }

    function tagsToArray(tagsArr)
    {
        tagsArr = updatedTags.split(",");
        return tagsArr
    }

    updatedTags = tagsToArray(updatedTags);

    const updateItemRequest =
        {
            'id': id,
            'name': updatedName,
            'description': updatedDescription,
            'price': updatedPrice,
            'category': updatedCategory,
            'tags': updatedTags,
            'picture': updatedPicture,
            //'isOnMenu': updatedAvailability
        }

    const response = await fetch(domain + "/api/item/update", {
        method: "PUT",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(updateItemRequest),
    });

    const updatedItem = await response.json();
    console.log(updatedItem);

    if (!response.ok)
    {
        errorModal();
    }
    else
    {
        const modalUpdate = document.getElementById("modalUpdateItem");

        modalUpdate.style.display = "none";

        successModal();
    }
}

async function availability()
{
    const div = document.getElementById('availability');
    if(div != null)
    {
        const response = await fetch(domain + '/api/item/getAll');

        if (!response.ok)
            throw new Error('There was an error.');

        const data = await response.json();

        for (let i = 0; i < data.length; i++)
        {
            const separator = document.createElement('br');
            const checkbox = document.createElement('input');
            checkbox.type = "checkbox";
            checkbox.name = "items";
            checkbox.value = data[i].id;
            checkbox.id = data[i].name;
            const label = document.createElement('label');
            label.htmlFor = data[i].name;
            label.appendChild(document.createTextNode(data[i].name));

            div.appendChild(checkbox);
            div.appendChild(label);
            div.appendChild(separator);

            if (data[i].isOnMenu === true)
            {
                document.getElementById(data[i].name).checked = true;
            }

            label.style.display = "inline";
        }
    }
}

function updateAvailability()
{
    successModal()
}

document.addEventListener('DOMContentLoaded', async function ()
{
    setTimeout(function() {
        const checkboxes = document.querySelectorAll("input[type=checkbox][name=items]");
        let updatedAvailability;
        let response2;

        checkboxes.forEach(function(checkbox)
        {
            checkbox.addEventListener('change', async function ()
            {
                const value = checkbox.value;
                console.log(value)
                const response = await fetch(domain + '/api/item/get/' + value);

                if (!response.ok)
                    throw new Error('There was an error.');

                const data = await response.json();
                console.log(data);

                updatedAvailability = !!checkbox.checked;

                let tagsArr = [];
                for (let j = 0; j < data.tags.length; j++)
                {
                    tagsArr.push((data.tags[j]).name);
                }

                const updateItemRequest =
                    {
                        'id': data.id,
                        'name': data.name,
                        'description': data.description,
                        'price': data.price,
                        'category': data.category.name,
                        'tags': tagsArr,
                        'picture': data.picture,
                        'isOnMenu': updatedAvailability
                    }

                response2 = await fetch(domain + "/api/item/update", {
                    method: "PUT",
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(updateItemRequest),
                });

                const updatedItem = await response2.json();
                console.log(updatedItem);

                if (!response2.ok)
                {
                    errorModal();
                }
            });
        });
    }, 5000);
});

function checkDelivery()
{
    const checkBox = document.getElementById("sameadr");
    const deliveryName = document.getElementById("deliveryName");
    const deliveryEmail = document.getElementById("deliveryEmail");
    const deliveryPhone = document.getElementById("deliveryPhone");
    const deliveryAddress = document.getElementById("deliveryAddress");

    if (checkBox.checked === true)
    {
        deliveryName.disabled = true;
        deliveryEmail.disabled = true;
        deliveryPhone.disabled = true;
        deliveryAddress.disabled = true;

        deliveryName.value = userName.toString();
        deliveryEmail.value = userEmail.toString();
        deliveryPhone.value = userPhone.toString();
        deliveryAddress.value = userAddress.toString();
    }
    else
    {
        deliveryName.value = "";
        deliveryEmail.value = "";
        deliveryPhone.value = "";
        deliveryAddress.value = "";

        deliveryName.disabled = false;
        deliveryEmail.disabled = false;
        deliveryPhone.disabled = false;
        deliveryAddress.disabled = false;
    }
}

function checkCardNumber()
{
    const cardNumber = document.getElementById('ccnum').value;
    const visa = document.getElementById('visa');
    const discover = document.getElementById('discover');
    const mastercard = document.getElementById('mastercard');
    const amex = document.getElementById('amex');

    if(cardNumber.startsWith("4"))
    {
        visa.style.fontSize = '23px';
        discover.style.color = '#a6a6a6';
        mastercard.style.color = '#a6a6a6';
        amex.style.color = '#a6a6a6';
    }
    else if(cardNumber.startsWith("3"))
    {
        amex.style.fontSize = '23px';
        discover.style.color = '#a6a6a6';
        mastercard.style.color = '#a6a6a6';
        visa.style.color = '#a6a6a6';
    }
    else if(cardNumber.startsWith("5"))
    {
        mastercard.style.fontSize= '23px';
        discover.style.color = '#a6a6a6';
        visa.style.color = '#a6a6a6';
        amex.style.color = '#a6a6a6';
    }
    else if(cardNumber.startsWith("6"))
    {
        discover.style.fontSize= '23px';
        visa.style.color = '#a6a6a6';
        mastercard.style.color = '#a6a6a6';
        amex.style.color = '#a6a6a6';
    }
    else
    {
        discover.style.fontSize= '22px';
        mastercard.style.fontSize= '22px';
        visa.style.fontSize= '22px';
        amex.style.fontSize= '22px';
        visa.style.color = '#1A1F71';
        mastercard.style.color = '#EB001B';
        amex.style.color = '#016FD0';
        discover.style.color = '#F9A021';
    }

}

async function getItemRemove()
{
    const select = document.getElementById('allItems');
    const value = select.options[select.selectedIndex].value;

    if(value !== 'null')
    {
        const response = await fetch(domain + '/api/item/get/' + value);

        if (!response.ok)
            throw new Error('There was an error.');

        const data = await response.json();

        document.getElementById('itemInfo').style.display = 'block';
        document.getElementById('itemNameRemove').innerHTML = (data.name).toString();
        document.getElementById('itemDescriptionRemove').innerHTML = (data.description).toString();
        document.getElementById('itemPriceRemove').innerHTML = "$" + ((data.price/100).toFixed(2)).toString();
        document.getElementById('itemCategoryRemove').innerHTML = (data.category.name).toString();
        let tagsArr = [];
        for (let i = 0; i < data.tags.length; i++)
        {
            tagsArr.push((data.tags[i]).name);
        }
        document.getElementById('itemTagsRemove').innerHTML = (tagsArr.toString());
        if(data.isOnMenu === true)
        {
            document.getElementById('itemAvailabilityRemove').innerHTML = "Item is on Menu";
            document.getElementById('itemAvailabilityRemove').style.textDecorationLine = 'underline';
            document.getElementById('itemAvailabilityRemove').style.textDecorationStyle = 'double';
        }
        else
        {
            document.getElementById('itemAvailabilityRemove').innerHTML = "Item is not on Menu";
        }
        // document.getElementById('itemPictureRemove').innerHTML = (data.picture).toString();
        const picture = document.getElementById('removePictureDisplay');
        picture.src = data.picture;
        picture.alt = data.name;

        document.getElementById('btnRemoveItem').disabled = false;
    }
}

function removeItem()
{
    const select = document.getElementById('allItems');
    const id = parseInt(select.options[select.selectedIndex].value);
    const itemName = select.options[select.selectedIndex].text;

    const modal = document.getElementById("modalConfirmDelete");
    const btnConfirm = document.getElementById("btnConfirmDelete");
    const btnCancel = document.getElementById("btnCancelDelete");
    const modalConfirmDelete = document.getElementById("modalConfirmDelete");
    document.getElementById("confirmationItemName").innerHTML = itemName;

    modal.style.display = "block";
    btnCancel.onclick = function()
    {
        modal.style.display = "none";
    }

    btnConfirm.onclick = async function ()
    {
        const response = await fetch(`http://localhost:8080/api/item/remove?id=${id}`, {
            method: "DELETE",
        });

        if (!response.ok)
        {
            modalConfirmDelete.style.display = "none";
            errorModal();
        }
        else
        {
            modalConfirmDelete.style.display = "none";

            successModal();
        }
    }
}