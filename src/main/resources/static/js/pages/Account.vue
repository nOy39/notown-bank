<!--todo сделать програмное возвращение на предыдущую страницу, сделать историю по счету-->
<template>
    <div>
        <b-card no-body>
            <b-tabs card>
                <b-tab no-body title="Current account" active>
                    <account-tab :accountInfo="currentAcc"></account-tab>
                </b-tab>
                <b-tab no-body title="History Operation">
                    <b-list-group>
                        <b-list-group-item>
                            History all transaction by current month. <a href="#">Detailed</a> history.
                        </b-list-group-item>
                        <b-list-group-item>
                            <HistoryTable
                                    :history="history"
                                    :gridColumns="gridColumns"></HistoryTable>
                        </b-list-group-item>
                        <b-list-group-item>
                        </b-list-group-item>
                        <b-list-group-item>Porta ac consectetur ac</b-list-group-item>
                        <b-list-group-item>
                        </b-list-group-item>
                    </b-list-group>
                </b-tab>
                <b-tab no-body title="History transaction">

                </b-tab>
                <b-tab title="Data">
                    <HistoryTable
                            :history="history"
                            :gridColumns="gridColumns"></HistoryTable>
                </b-tab>
            </b-tabs>
        </b-card>
        <router-link to="/account">account</router-link>
    </div>
</template>

<script>
    import {AXIOS} from "../http-common";
    import HistoryTable from "../components/data/HistoryTable.vue";
    import AccountTab from "../components/data/AccountTab.vue";

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
            console.log('bm')
            this.fetchAccountData()
        },
        mounted() {
            console.log('m')
        },
        beforeUpdate() {
            console.log('bu')
            if (this.currentAcc.uniqCheckId != this.$route.params.id) {
                this.fetchAccountData()
            }
        },
        updated() {
            console.log('u')
        },
        computed: {
            acc() {
                return this.$route.params.id
            },
            currentAcc() {
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
                        'Authorization': sessionStorage.getItem('auth')
                    }
                })
                    .then(response => {
                        this.$store.dispatch('setCurrentAcc', response.data)
                            .then(() => console.log(response))
                    })
                    .then(() => {
                        AXIOS({
                            method: 'post',
                            url: '/logs/period/',
                            headers: {
                                'Authorization': sessionStorage.getItem('auth')
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
                                        console.log(this.data)
                                        this.$store.dispatch('isLoading', false)
                                    })
                                    .catch(e => {
                                        this.$store.dispatch('setError', e.message)
                                    })
                            })
                            .catch(e => {
                                console.log('history: ' + e)
                                this.$store.dispatch('setError', e.message)
                            })
                    })
                    .catch(e => {
                        console.log('console: ' + e)
                        this.$store.dispatch('setError', e.message)
                    })
            },
            styleTr(inc, out) {
                if (inc > out) {
                    return {color: 'green'}
                } else {
                    return {color: 'red'}
                }
            },
            search(){}
        }
    }
</script>

<style scoped>

</style>
