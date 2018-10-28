<template>
    <div>
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
                                    <dd class="col-sm-9"><em>{{ moment(currentAcc.updateAt).format('LLL') }}</em></dd>
                                </dl>
                            </b-list-group-item>
                        </b-list-group>
                    </b-card>
                </b-tab>
                <b-tab no-body title="History Operation">
                    <account-tab></account-tab>
                </b-tab>
                <b-tab no-body title="History transaction">
                    <b-list-group>
                        <b-list-group-item>
                            History all transaction by current month
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
    import HistoryTable from "../components/data/HistoryTable.vue";
    import AccountTab from "../components/data/AccountTab.vue";

    export default {
        name: "Test",
        components: {AccountTab, HistoryTable},
        data() {
            return {
                currentAcc: {
                    blocked: false,
                    createdAt: "2018-10-17T07:29:01.471",
                    createdBy: 8,
                    currency: "RUB",
                    default: false,
                    sum: 10082.92,
                    type: "DEBIT",
                    uniqCheckId: "72673766-d279-4196-9919-67e142cc5ae0",
                    updateAt: "2018-10-21T07:29:01.471",
                    updateBy: 8
                },
                history: [
                    {
                        transactionDate: "2018-10-24T17:08:53.804",
                        id: 746,
                        incomingSum: 2440,
                        outgoingSum: 0,
                        transactionAccount: '472673766-d279-4196-9919-67e142cc5ae0',
                        transactionClient: 'Виктор Д...',
                    },
                    {
                        transactionDate: "2018-10-17T17:08:53.804",
                        id: 76,
                        incomingSum: 1220,
                        outgoingSum: 0,
                        transactionAccount: '72673766-d279-4196-9919-67e142cc5ae0',
                        transactionClient: 'Виктор Д...',
                    },
                    {
                        transactionDate: "2018-10-24T17:09:53.804",
                        id: 276,
                        incomingSum: 0,
                        outgoingSum: 250.5,
                        transactionAccount: '72673766-d279-4196-9919-67e142cc5ae0',
                        transactionClient: 'Виктор Д...',
                    },
                    {
                        transactionDate: "2018-10-20T17:08:53.804",
                        id: 176,
                        incomingSum: 0,
                        outgoingSum: 4250.5,
                        transactionAccount: '372673766-d279-4196-9919-67e142cc5ae0',
                        transactionClient: 'Виктор Д...',
                    },
                    {
                        transactionDate: "2018-10-18T17:08:53.804",
                        id: 376,
                        incomingSum: 0,
                        outgoingSum: 1250.5,
                        transactionAccount: '726673766-d279-4196-9919-67e142cc5ae0',
                        transactionClient: 'Виктор Д...',
                    },
                ],
                gridColumns: ['id', 'transactionDate', 'incomingSum', 'outgoingSum', 'transactionAccount', 'transactionClient',],
                search: ''
            }
        },
        methods: {
            styleTr(inc, out) {
                if (inc > out) {
                    return {color: 'green'}
                } else {
                    return {color: 'red'}
                }
            }
        }
    }
</script>

<style scoped>

</style>
