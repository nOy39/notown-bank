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

                </b-tab>
                <b-tab no-body title="History transaction">
                    <b-list-group>
                        <b-list-group-item>

                        </b-list-group-item>
                        <b-list-group-item>Dapibus ac facilisis in</b-list-group-item>
                        <b-list-group-item><table class="table table-hover">
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
                                    <em>{{item.incomingSum > item.outgoingSum ? item.incomingSum : item.outgoingSum}}</em>
                                </td>
                                <td>
                                    <em>{{item.transactionClient.firstName + ' ' + item.transactionClient.lastName
                                        .substring(0,1).toUpperCase() + "."}}</em>
                                </td>
                                <td>
                                    <em>{{item.transactionAccount.uniqCheckId}}</em>
                                </td>
                            </tr>
                            </tbody>
                        </table></b-list-group-item>
                        <b-list-group-item>Porta ac consectetur ac</b-list-group-item>
                        <b-list-group-item>
                        </b-list-group-item>
                    </b-list-group>
                </b-tab>
                <b-tab title="Text">

                </b-tab>
            </b-tabs>
        </b-card>
        <router-link to="/account">account</router-link>
    </div>
</template>

<script>
    export default {
        name: "Test",
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
                        createdAt: "2018-10-24T17:08:53.804",
                        id: 76,
                        incomingSum: 0,
                        outgoingSum: 250.5,
                        transactionAccount: {
                            blocked: false,
                            createdAt: "2018-10-16T13:47:32.141",
                            currency: {
                                name: "USD"
                            },
                            default: true,
                            id: 39,
                            sum: 297.6,
                            uniqCheckId: "38b2a97d-c3ca-4749-876c-088fbb2c6636",
                            updateBy: 8,
                            updatedAt: "2018-10-24T17:42:30.889"

                        },
                        transactionClient: {
                            active: true,
                            createdAt: "2018-10-11T13:57:56.155",
                            dateOfBirth: "2000-10-10",
                            email: "client@admin.com",
                            firstName: "Виктор",
                            id: 11,
                            lastName: "дробышев",
                            phone: 9105552211,
                            updateBy: 8,
                            updatedAt: "2018-10-11T13:57:56.155"
                        },
                    },
                    {
                        createdAt: "2018-10-24T17:40:51.622",
                        id: 90,
                        incomingSum: 720.77,
                        outgoingSum: 0,
                        transactionAccount: {
                            blocked: false,
                            createdAt: "2018-10-16T13:47:32.141",
                            currency: {
                                name: "USD"
                            },
                            default: true,
                            id: 39,
                            sum: 297.6,
                            uniqCheckId: "38b2a97d-c3ca-4749-876c-088fbb2c6636",
                            updateBy: 8,
                            updatedAt: "2018-10-24T17:42:30.889"

                        },
                        transactionClient: {
                            active: true,
                            createdAt: "2018-10-11T13:57:56.155",
                            dateOfBirth: "2000-10-10",
                            email: "client@admin.com",
                            firstName: "Виктор",
                            id: 11,
                            lastName: "дробышев",
                            phone: 9105552211,
                            updateBy: 8,
                            updatedAt: "2018-10-11T13:57:56.155"
                        },
                    },
                    {
                        createdAt: "2018-10-24T17:27:57.902",
                        id: 86,
                        incomingSum: 0,
                        outgoingSum: 1000.5,
                        transactionAccount: {
                            blocked: false,
                            createdAt: "2018-10-16T13:47:32.141",
                            currency: {
                                name: "USD"
                            },
                            default: true,
                            id: 39,
                            sum: 297.6,
                            uniqCheckId: "38b2a97d-c3ca-4749-876c-088fbb2c6636",
                            updateBy: 8,
                            updatedAt: "2018-10-24T17:42:30.889"

                        },
                        transactionClient: {
                            active: true,
                            createdAt: "2018-10-11T13:57:56.155",
                            dateOfBirth: "2000-10-10",
                            email: "client@admin.com",
                            firstName: "Виктор",
                            id: 11,
                            lastName: "дробышев",
                            phone: 9105552211,
                            updateBy: 8,
                            updatedAt: "2018-10-11T13:57:56.155"
                        }

                    },
                    {
                        createdAt: "2018-10-24T17:08:53.804",
                        id: 73,
                        incomingSum: 0,
                        outgoingSum: 250.5,
                        transactionAccount: {
                            blocked: false,
                            createdAt: "2018-10-16T13:47:32.141",
                            currency: {
                                name: "USD"
                            },
                            default: true,
                            id: 39,
                            sum: 297.6,
                            uniqCheckId: "38b2a97d-c3ca-4749-876c-088fbb2c6636",
                            updateBy: 8,
                            updatedAt: "2018-10-24T17:42:30.889"

                        },
                        transactionClient: {
                            active: true,
                            createdAt: "2018-10-11T13:57:56.155",
                            dateOfBirth: "2000-10-10",
                            email: "client@admin.com",
                            firstName: "Виктор",
                            id: 11,
                            lastName: "дробышев",
                            phone: 9105552211,
                            updateBy: 8,
                            updatedAt: "2018-10-11T13:57:56.155"
                        }
                    }
                ],
                fields: [
                    {
                        key: 'id',
                        sortable: true
                    },
                    {
                        key: 'createdAt',
                        label: 'Date transaction',
                        sortable: true
                    },
                    {
                        key: 'transactionAccount.uniqCheckId',
                        label: 'Person age',
                        sortable: true,
                        // Variant applies to the whole column, including the header and footer
                        variant: 'danger'
                    }
                ],
                cssObject:{
                    fad: 'fas fa-sort-down',
                    fau: 'fas fa-sort-up'
                }

            }
        },
        methods: {
            styleTr(inc, out) {
                if (inc > out) {
                    this.incomin = true
                    return {color: 'green'}
                } else {
                    this.incomin = false
                    return {color: 'red'}
                }
            }
        }
    }
</script>

<style scoped>

</style>
