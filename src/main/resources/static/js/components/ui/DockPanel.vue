<template>
    <div>
        <b-card-group deck class="mb-3 my-3">
            <b-card border-variant="success"
                    header="CLIENT SERVICE"
                    align="center">
                <div class="mx-auto">
                    <ul id="badge-list">
                        <li v-for="item in items">
                            <router-link :to="item.url">
                                <i :class="item.icon"></i>
                                <p>{{item.name}}</p>
                            </router-link>
                        </li>
                    </ul>
                    <p>Quick access to most popular services</p>
                    <div slot="footer">
                        <small class="text-muted">Try to spend your money</small>
                    </div>
                </div>
            </b-card>
            <b-card border-variant="secondary"
                    header="Last Transaction"
                    header-border-variant="secondary"
                    align="center">
                <div v-for="item in history">
                    <b-list-group>
                        <b-list-group-item class="d-flex justify-content-between align-items-center">
                            <small>{{moment(item.transactionDate).format('LLL')}}</small>
                            <small><em>{{item.transactionAccount}}</em></small>
                            <div slot="footer">
                                <small class="text-muted">{{item.transactionClient}}</small>
                            </div>
                            <b-badge variant="primary" pill>{{item.incomingSum < item.outgoingSum ? '- ' + item.outgoingSum : item.incomingSum}}</b-badge>
                        </b-list-group-item>
                    </b-list-group>
                </div>
            </b-card>
        </b-card-group>
    </div>
</template>

<script>
    export default {
        name: "DockPanel",
        props: {
            history: Array
        },
        data() {
            return {
                items:[
                    {icon: "fas fa-clipboard-list fa-3x", name: 'Create New Account', url: '/account'},
                    {icon: "fas fa-piggy-bank fa-3x", name: 'Open Credit', url: '/credit'},
                    {icon: "far fa-money-bill-alt fa-3x", name: 'Create Payment', url: '/payment'},
                    {icon: "fas fa-euro-sign fa-3x", name: 'Buy/Sell Currency', url: '/currency'},
                    {icon: "fas fas fa-history fa-3x", name: 'History', url: '/history'}
                ]
            }
        }
    }
</script>

<style scoped>
    ul {
        list-style: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        position: center;
    }

    li {
        float: left;
    }

    li a {
        display: block;
        color: steelblue;
        text-align: center;
        padding: 35px;
        text-decoration: none;
    }

    li a:hover {
        color: darkcyan;
    }
</style>
