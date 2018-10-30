<template>
    <div>
        <h3>
            {{routeItem}}
        </h3>
        <button type="button" @click="getRoute">GetRoute</button>
        <ul>
            <li v-for="item in items">
                <router-link :to="'/'+item.url">{{item}}</router-link>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "TestSub",
        computed: {
            items() {
                return this.$store.getters.getItems
            },
            routeItem() {
                return this.items[parseInt(this.getRoute)]
            },
            getRoute() {
                console.log(this.$route.params.id)
                return this.$route.params.id
            }
        },
        methods: {
            getRoute() {
                console.log(this.$route.params.id)
            }
        }
    }
</script>

<style scoped>

</style>
