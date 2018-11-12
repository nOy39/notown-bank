<template>
    <div>
        <b-card title="Browse" class="mb-2"
                sub-title="Browse personal accounts data">
            <table align="center">
                <tr>
                    <td colspan="6"><h3>Browse your accounts</h3></td>
                </tr>
                <tr>
                    <th>Account</th>
                    <th>Card Number</th>
                    <th>Status</th>
                    <th>Type</th>
                    <th>Currency</th>
                    <th>Balance</th>
                </tr>
                <tr v-for="item in accounts">
                    <td>
                        <router-link :to="'/account/'+item.accountNumber">{{item.accountNumber}}</router-link>
                    </td>
                    <td>{{item.cardNumber}}</td>
                    <td>{{!item.blocked ? 'Active' : 'Blocked'}}</td>
                    <td>{{item.type.typeName}}</td>
                    <td>{{item.currency.currencyName}}</td>
                    <td>{{item.sum}}</td>
                </tr>
            </table>
        </b-card>

    </div>
</template>

<script>
    export default {
        props: {
            accounts: Array
        },
        data() {
            return {
            }
        }
    }
</script>

<style scoped>
    table {
        width: 100%;
    }
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
        opacity: 0.95;
    }
    th, td {
        padding: 10px;
        text-align: center;
    }
    th {
        background-color: steelblue;
        color: white;
    }
    tr:nth-child(even) {
        background-color: lightgray;
    }
    tr:nth-child(odd) {
        background-color: whitesmoke;
    }
</style>
