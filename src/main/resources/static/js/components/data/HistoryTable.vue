<template>
    <div>

        <b-col sm="9">
            <div>
                <b-form-input v-model="searchKey"
                              type="text"
                              placeholder="SEARCH(Account ID, sum, date, Name"
                              class="my-1">
                </b-form-input>

            </div>
            <table>
                <thead>
                <tr>
                    <th v-for="key in gridColumns"
                        @click="sortBy(key)"
                        :class="{active: sortKey == key}">
                        {{key | capitalize}}
                        <span class="arrow"
                              :class="sortOrders[key] > 0 ? 'asc' : 'dsc'"></span>
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr id="table-row" v-for="entry in filteredData">
                    <td v-for="key in gridColumns">
                        <b :style="key === 'incomingSum' ? 'color: green' : 'color: red'"
                           v-if="key === 'incomingSum' || key === 'outgoingSum'">
                            {{entry[key]}}
                        </b>
                        <em v-else-if="key === 'transactionDate'">
                            {{ moment(entry[key]).format('DD MMMM YYYY, HH:MM')}}
                        </em>
                        <p v-else>
                            {{ entry[key] }}
                        </p>
                    </td>
                </tr>
                </tbody>
            </table>
        </b-col>
    </div>
</template>

<script>
    export default {
        name: "HistoryTable",
        props: {
            history: Array,
            gridColumns: Array
        },
        data() {
            let sortOrders = {}
            this.gridColumns.forEach(function (key) {
                sortOrders[key] = 1
            })
            return {
                searchKey: '',
                sortKey: '',
                sortOrders: sortOrders
            }
        },
        methods: {
            sortBy(key) {
                this.sortKey = key
                this.sortOrders[key] = this.sortOrders[key] * -1
            }
        },
        computed: {
            filteredData() {
                var sortKey = this.sortKey
                var filterKey = this.searchKey && this.searchKey.toLowerCase()
                var order = this.sortOrders[sortKey] || 1
                var data = this.history
                if (filterKey) {
                    data = data.filter((row) => {
                        return Object.keys(row).some((key) => {
                            return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                        })
                    })
                }
                if (sortKey) {
                    data = data.slice().sort((a, b) => {
                        a = a[sortKey]
                        b = b[sortKey]
                        return (a === b ? 0 : a > b ? 1 : -1) * order
                    })
                }
                return data
            }
        },
        filters: {
            capitalize(str) {
                return str.charAt(0).toUpperCase() + str.slice(1)
            },
            formatDate(obj) {
                for (var key in obj) {
                    if (key === 'transactionDate')
                        console.log(obj[key])
                    return this.moment().format('DD-MM-YYYY HH:mm')
                }
            }
        }
    }
</script>

<style scoped>
    body {

    }

    table {
        font-family: Helvetica Neue, Arial, sans-serif;
        font-size: 14px;
        color: #444;
        border: 2px solid cornflowerblue;
        border-radius: 3px;
        background-color: #fff;
        width: 100%;
    }

    th {
        background-color: cornflowerblue;
        color: rgba(255, 255, 255, 0.66);
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }

    td {
        background-color: #f9f9f9;
    }

    tr {
        border: gray 1px solid;
    }

    th, td {
        min-width: 120px;
        padding: 10px 20px;
    }

    th.active {
        color: #fff;
    }

    th.active .arrow {
        opacity: 1;
    }

    .arrow {
        display: inline-block;
        vertical-align: middle;
        width: 0;
        height: 0;
        margin-left: 5px;
        opacity: 0.66;
    }

    .arrow.asc {
        border-left: 4px solid transparent;
        border-right: 4px solid transparent;
        border-bottom: 4px solid #fff;
    }

    .arrow.dsc {
        border-left: 4px solid transparent;
        border-right: 4px solid transparent;
        border-top: 4px solid #fff;
    }
</style>
