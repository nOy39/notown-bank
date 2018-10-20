<template>
    <div>
        <h3>Test vue</h3>
        <input type="text" v-model="usernameOrEmail"/>
        <input type="password" v-model="password"/>
        <hr>
        <button type="button" @click="auth(usernameOrEmail, password)">Save</button>
        <button type="button" @click="getTest">Test</button>
        <hr>
        <h2>{{test}}</h2>
        <hr>
        <router-link to="/test">test</router-link>
        <hr>
        <router-view></router-view>
    </div>
</template>

<script>
    import {AXIOS} from "../http-common";

    export default {
        name: "App",
        data () {
            return {
                usernameOrEmail: '',
                password: '',
            }
        },
        methods: {
            auth(us, pass) {

            },
            getTest() {
                AXIOS.get('/test')
                    .then(response => {
                        this.$store.dispatch('setTest', response.data)
                    })
            }
        },
        computed: {
            test() {
                return this.$store.getters.getTest
            }
        }
    }
</script>

<style scoped>

</style>
