<!--todo сделать програмное возвращение на предыдущую страницу, сделать историю по счету-->
<template>
    <div>
        <b-card no-body v-if="accountInfo">
            <account-tab :accountInfo="accountInfo"></account-tab>
        </b-card>
    </div>
</template>

<script>
    import {AXIOS} from "../../http-common";
    import HistoryTable from "../../components/data/HistoryTable.vue";
    import AccountTab from "../../components/data/AccountTab.vue";

    export default {
        name: "Account",
        components: {AccountTab, HistoryTable},
        data() {
            return {
                uniqCheckId: this.$route.params.id,
                gridColumns: ['id', 'transactionDate', 'incomingSum', 'outgoingSum', 'transactionAccount', 'transactionClient'],
                search: '',
                firstDate: this.moment().format('YYYY-MM') + '-01T00:00:00',
                lastDate: this.moment().format('YYYY-MM-DDTHH:mm:ss')
            }
        },
        beforeMount() {
            this.fetchAccountData()
        },
        computed: {
            acc() {
                return this.$route.params.id
            },
            accountInfo() {
                return this.$store.getters.getCurrentAcc
            },
            loading() {
                return this.$store.getters.getLoading
            },
            history() {
                return this.$store.getters.getHistory
            }
        },
        methods: {
            fetchAccountData() {
                this.$store.dispatch('isLoading', true)
                AXIOS({
                    method: 'get',
                    url: '/account/' + this.$route.params.id,
                    headers: {
                        'Authorization': 'Bearer ' + this.$store.getters.getToken
                    }
                })
                    .then(response => {
                        this.$store.dispatch('setCurrentAcc', response.data)
                        console.log(response)
                    })
                    .then(() => {
                        AXIOS({
                            method: 'post',
                            url: '/logs/period/',
                            headers: {
                                'Authorization': 'Bearer ' + this.$store.getters.getToken
                            },
                            data: {
                                uniqCheckId: this.$route.params.id,
                                firstDate: this.firstDate,
                                lastDate: this.lastDate
                            }
                        })
                            .then(response => {
                                this.$store.dispatch('setHistory', response.data)
                                    .then(() => {
                                        this.$store.dispatch('isLoading', false)
                                    })
                                    .catch(e => {
                                        this.$store.dispatch('setError', e.message)
                                        this.$store.dispatch('isLoading', true)
                                    })
                            })
                            .catch(e => {
                                this.$store.dispatch('setError', e.message)
                                this.$store.dispatch('isLoading', true)
                            })
                    })
                    .catch(e => {
                        if (e.response.data) {
                            this.$store.dispatch('setResponseMessage', e.response.data)
                        } else {
                            this.$store.dispatch('setError', e.message)
                        }
                        this.$store.dispatch('isLoading', false)
                    })
            }
        }
    }
</script>

<style scoped>

</style>
