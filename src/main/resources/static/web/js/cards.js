Vue.createApp({
    data() {
        return {
            clientInfo: {},
            creditCards: [],
            debitCards: [],
            errorToats: null,
            errorMsg: null,
        }
    },
    methods: {
        getData: function () {
            axios.get("/api/clients/current")
                .then((response) => {
                    this.clientInfo = response.data;
                    this.clientInfo.cards.forEach(card => {
                 const expiredCard = this.isCardExpired(card.thruDate);
                     card.isExpired = expiredCard;
                     card.textColor = expiredCard ? 'red' : 'black';
                    });

                    this.creditCards = this.clientInfo.cards.filter(card => card.type == "CREDIT");
                    this.debitCards = this.clientInfo.cards.filter(card => card.type == "DEBIT");
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                });
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed";
                    this.errorToats.show();
                });
        },

        isCardExpired: function (thruDate) {
            const currentDate = new Date();
            const cardDate = new Date(thruDate);
            return cardDate < currentDate;
        },
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.getData();
    }
}).mount('#app');