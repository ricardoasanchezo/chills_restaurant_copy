class OrderTicket extends HTMLElement
{
    orderId;
    createdAt;
    closedAt;
    orderType;
    customerEmail;
    orderOwnerEmail;
    items;
    state;
    comments;

    constructor(orderDto)
    {
        super();

        this.attachShadow({mode: 'open'});

        // private Long id;
        // private String orderCreatorEmail;
        // private String orderOwnerEmail;
        // private LocalDateTime createdAt;
        // private LocalDateTime closedAt;
        // private List<ItemDto> items;
        // private String customerComments;
        // private String orderType;
        // private String orderState;
        // private PaymentDto payment;

        this.orderId = orderDto.id;
        this.createdAt = new Date(orderDto.createdAt);
        this.closedAt = (orderDto.closedAt !== null) ? new Date(orderDto.closedAt) : null;
        this.orderType = orderDto.orderType;
        this.customerEmail = orderDto.orderCreatorEmail;
        this.orderOwnerEmail = orderDto.orderOwnerEmail;
        this.items = orderDto.items;
        this.state = orderDto.orderState;
        this.comments = orderDto.customerComments;
    }

    connectedCallback()
    {
        this.render();
    }

    render()
    {
        this.shadowRoot.innerHTML = `
        <style>
            :host {
                background-color: whitesmoke;
                padding: 10px;
                width: 350px;
                box-shadow: 5px 5px 10px #aaaaaa;
                
                font-family: monospace;
                font-size: 18px;
            }
            
            :host * {
                box-sizing: border-box;
            }
            
            #order-id {
                font-size: 30px;
                font-weight: bold;
            }
            
            order-timer {
              align-items: center;
              justify-content: center;
              padding: 1rem 1.5rem 1rem 1.5rem;
              width: fit-content;
              border-radius: 200px;
              font-family: monospace;
              font-size: 2rem;
              font-weight: bold;  
              transition: background-color 1s, color 1s;
            }
            
            #items {
                margin: 20px 0 20px 0;
            }
            
            .item {
                margin: 10px;
            }
            
            .datetime {
                display: flex;
                justify-content: space-between;
            }
            
            .flex-space-between {
                display: flex;
                justify-content: space-between;
            }
            
            .text-center {
                text-align: center;
            }
        </style>
        
        <div id="header" class="flex-space-between">
            <div>
                <div id="order-id">#${this.orderId}</div>
                <div>${this.state}</div>
            </div>
        </div>
        
        <div id="button-container"></div>
        
        <div>Customer: ${this.customerEmail || 'Not found'}</div>
        <div id="order-owner-container">
            <span>Owner: ${this.orderOwnerEmail || 'No owner'}</span>
        </div>
        
        <hr>
        
        <div class="flex-space-between">
            <span>${this.createdAt.toDateString()}</span>
            <span>${this.createdAt.toTimeString().substring(0,8)}</span>
        </div>
        
        <hr>

        <div class="text-center">${this.orderType}</div>

        <hr>

        <div id="items">
            ${this.items.map(item => `<div class="item">${item.name}</div>`).join('\n')}
        </div>

        <hr>

        <div id="comments">${(this.comments.length) > 0 ? this.comments : 'No comments'}</div>
        `;

        let buttonContainer= this.shadowRoot.querySelector('#button-container');

        if (this.closedAt !== null)
        {
            buttonContainer.innerText = `This order was closed ${this.closedAt.toDateString()} at ${this.closedAt.toTimeString().substring(0,8)}`;
            return;
        }

        this.shadowRoot.querySelector('#header').appendChild(new OrderTimer(this.createdAt));

        buttonContainer.appendChild(this.createStateButton('Canceled'));
        buttonContainer.appendChild(this.createStateButton('Fulfilled'));

        if (this.state !== 'In Progress')
            buttonContainer.appendChild(this.createStateButton('In Progress'));

        if (this.state !== 'Ready')
            buttonContainer.appendChild(this.createStateButton('Ready'));

        let ownershipButton = document.createElement('button');
        ownershipButton.innerText = 'Claim ownership';

        ownershipButton.addEventListener('click', () => this.claimOwnership());

        this.shadowRoot.querySelector('#order-owner-container').appendChild(ownershipButton);
    }

    createStateButton(state)
    {
        let button = document.createElement('button');
        button.innerText = state;
        button.addEventListener('click', () => this.updateOrderState(state));
        return button;
    }



    static get observedAttributes()
    {
        return ['order-id', 'state', 'order-owner'];
    }

    attributeChangedCallback(name, oldValue, newValue)
    {
        if (!this.isConnected)
            return;

        switch (name)
        {
            case 'order-id':
                this.orderId = newValue || '';
                break;
            case 'state':
                this.state = newValue || '';
                break;
            case 'order-owner':
                this.orderOwnerEmail = newValue || '';
                break;
        }

        this.render();
    }

    async updateOrderState(state)
    {
        // IN_PROGRESS("In Progress") // Being cooked
        // READY("Ready")             // Finished cooking but hasn't been given to customer
        // FULFILLED("Fulfilled")     // Has been given to customer
        // CANCELED("Canceled")

        let buttons = this.shadowRoot.querySelector('#button-container').children;
        for (let i = 0; i < buttons.length; i++)
        {
            buttons[i].disabled = true;
        }

        // let url = `${domain}/api/orders/updateState?id=${this.orderId}&state=${state}`;
        let url = `/api/orders/updateState?id=${this.orderId}&state=${state}`;

        const response = await fetch(url, { method: "PUT" });

        if (!response.ok)
        {
            alert(`The request to update the state of the order ${this.orderId} to ${state} failed!`);
            return;
        }

        for (let i = 0; i < buttons.length; i++)
        {
            buttons[i].disabled = false;
        }

        const orderDto = await response.json();

        this.closedAt = this.closedAt = (orderDto.closedAt !== null) ? new Date(orderDto.closedAt) : null;
        this.setAttribute('state', orderDto.orderState);
    }

    async claimOwnership()
    {
        // let url = `${domain}/api/orders/claimOwnership?id=${this.orderId}`;
        let url = `/api/orders/claimOwnership?id=${this.orderId}`;

        const response = await fetch(url, { method: "PUT" });

        if (!response.ok)
        {
            alert(`The request to claim ownership of the order ${this.orderId} failed!`);
            return;
        }

        const orderDto = await response.json();

        this.setAttribute('order-owner', orderDto.orderOwnerEmail);
    }
}

customElements.define('order-ticket', OrderTicket);