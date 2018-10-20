<template>
    <div>
        <h3>Test vue</h3>
        <input type="text" v-model="usernameOrEmail"/>
        <input type="password" v-model="password"/>
        <hr>
        <button type="button" @click="auth">Save</button>
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
            auth() {
                AXIOS({
                    method: 'post',
                    url: '/auth/signin',
                    data: {
                        usernameOrEmail: this.usernameOrEmail,
                        password: this.password
                    }
                })
                    .then(response => {
                        this.$store.dispatch('setUser', response.data)
                    })
                    .catch(e => {
                        console.log(e)
                    })
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
