class OrderTimer extends HTMLElement
{
    static maxMillis = 1200000;

    startMillis;

    constructor(date = new Date())
    {
        super();

        this.startMillis = date.getTime();
    }

    connectedCallback()
    {
        this.update();
    }

    update()
    {
        let difInMillis = new Date().getTime() - this.startMillis;

        let hue = this.calcHue(difInMillis);

        this.style.backgroundColor = `hsl(${hue},100%,70%)`;
        this.style.color = `hsl(${hue},100%,20%)`;

        let minutes = (difInMillis / 60000) | 0;
        difInMillis -= minutes * 60000;
        let seconds = (difInMillis / 1000) | 0;

        this.innerText = String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0');

        if (minutes > 60)
        {
            this.innerText = '>hour';
            return;
        }

        setTimeout(() => this.update(), 1000);
    }

    calcHue(difInMillis)
    {
        let ratio = difInMillis / OrderTimer.maxMillis;
        let hue = 120 - (ratio * 120); // Green hue is 120, red hue is 0
        return hue > 0 ? hue : 0;
    }
}

window.customElements.define('order-timer', OrderTimer);