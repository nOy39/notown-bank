<!--todo сделать програмное возвращение на предыдущую страницу, сделать историю по счету-->
<template>
    <div v-if="!loading">
        <div v-show="currentAcc">
            <b-card no-body>
                <b-tabs card>
                    <b-tab no-body title="Current account" active>
                        <b-card header="Current bank account info"
                                header-tag="header"
                                :footer="'<em>Balance: </em><b>'+currentAcc.sum+'</b>'"
                                footer-tag="footer"
                                :title="'ACCOUNT NUMBER: <em>' + currentAcc.uniqCheckId + '</em>'">
                            <b-list-group>
                                <b-list-group-item>
                                    <dl class="row">
                                        <dt class="col-sm-3">Account type:</dt>
                                        <dd class="col-sm-9"><em>{{currentAcc.type}}</em></dd>
                                    </dl>
                                </b-list-group-item>
                                <b-list-group-item>
                                    <dl class="row">
                                        <dt class="col-sm-3">Currency:</dt>
                                        <dd class="col-sm-9"><em>{{currentAcc.currency}}</em></dd>
                                    </dl>
                                </b-list-group-item>
                                <b-list-group-item>
                                    <dl class="row">
                                        <dt class="col-sm-3">Last updated:</dt>
                                        <dd class="col-sm-9"><em>{{ moment(currentAcc.updatedAt).format('LLL') }}</em>
                                        </dd>
                                    </dl>
                                </b-list-group-item>
                            </b-list-group>
                        </b-card>
                    </b-tab>
                    <b-tab no-body title="Account History">
                        <b-list-group>
                            <b-list-group-item>
                                <b-row class="my-1">
                                    <b-col sm="9">
                                        <b-form-input
                                                id="input-none"
                                                :state="null"
                                                type="text"
                                                placeholder="Search (enter UUID, sum, client)"
                                                v-model="search"></b-form-input>
                                    </b-col>
                                </b-row>
                            </b-list-group-item>
                            <b-list-group-item>Dapibus ac facilisis in</b-list-group-item>
                            <b-list-group-item>
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Date transaction</th>
                                        <th scope="col">Sum transaction</th>
                                        <th scope="col">Client transaction</th>
                                        <th scope="col">Account transaction</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="item in history" :key="item.id">
                                        <th scope="row">{{item.id}}</th>
                                        <td>{{moment(item.createdAt).format('lll')}}</td>
                                        <td :style="styleTr(item.incomingSum, item.outgoingSum)">
                                            <i :class="item.incomingSum > item.outgoingSum ? 'fas fa-sort-up' : 'fas fa-sort-down'"></i>
                                            <em>{{item.incomingSum > item.outgoingSum ? item.incomingSum :
                                                item.outgoingSum}}</em>
                                        </td>
                                        <td>
                                            <em>{{item.transactionClient.firstName + ' ' +
                                                item.transactionClient.lastName
                                                .substring(0,1).toUpperCase() + "."}}</em>
                                        </td>
                                        <td>
                                            <em>{{item.transactionAccount.uniqCheckId}}</em>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </b-list-group-item>
                            <b-list-group-item>Porta ac consectetur ac</b-list-group-item>
                            <b-list-group-item>
                            </b-list-group-item>
                        </b-list-group>
                    </b-tab>
                    <b-tab title="Text">
                        <h5>This tab does not have the <code>no-body</code> prop set</h5>
                        Quis magna Lorem anim amet ipsum do mollit sit cillum voluptate ex nulla
                        tempor. Laborum consequat non elit enim exercitation cillum aliqua consequat
                        id aliqua. Esse ex consectetur mollit voluptate est in duis laboris ad sit
                        ipsum anim Lorem. Incididunt veniam velit elit elit veniam Lorem aliqua quis
                        ullamco deserunt sit enim elit aliqua esse irure.
                    </b-tab>
                </b-tabs>
            </b-card>
        </div>
        <router-link to="/">main</router-link>
    </div>
</template>

<script>
    import {AXIOS} from "../http-common";

    export default {
        name: "Account",
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
            },
            data() {
                return {
                    uniqCheckId: this.$route.params.id,
                    firstDate: this.moment().format('YYYY-MM') + '-01T00:00:00',
                    lastDate: this.moment().format('YYYY-MM-DDTHH:mm:ss')
                }
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
                            data: this.data
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
