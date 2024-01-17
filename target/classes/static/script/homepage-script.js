async function imageGallery()
{
    const response = await fetch(domain + '/api/item/get/' + 1);
    const response2 = await fetch(domain + '/api/item/get/' + 2);
    const response3 = await fetch(domain + '/api/item/get/' + 3);
    const response4 = await fetch(domain + '/api/item/get/' + 152);

    if (!response.ok || !response2.ok || !response3.ok || !response4.ok)
        throw new Error('There was an error.');

    const item1 = await response.json();
    const item2 = await response2.json();
    const item3 = await response3.json();
    const item4 = await response4.json();

    const display1 = document.getElementById('item1');
    const display2 = document.getElementById('item2');
    const display3 = document.getElementById('item3');
    const display4 = document.getElementById('item4');

    const desc1 = document.getElementById('descItem1');
    const desc2 = document.getElementById('descItem2');
    const desc3 = document.getElementById('descItem3');
    const desc4 = document.getElementById('descItem4');

    display1.src = item1.picture
    display1.alt = item1.name
    desc1.innerHTML = item1.description

    display2.src = item2.picture
    display2.alt = item2.name
    desc2.innerHTML = item2.description

    display3.src = item3.picture
    display3.alt = item3.name
    desc3.innerHTML = item3.description

    display4.src = item4.picture
    display4.alt = item4.name
    desc4.innerHTML = item4.description
}