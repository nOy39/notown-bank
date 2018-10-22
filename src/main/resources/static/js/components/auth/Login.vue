<template>
    <div>
        <h2></h2>
        <input type="text" v-model="usernameOrEmail"/>
        <input type="password" v-model="password"/>
        <button type="button" @click="logIn" :disabled="loading || enableLs">Save</button>
        <button type="button" @click="signIn" :disabled="!enableLs">signIn</button>
    </div>
</template>

<script>
    import {AXIOS} from "../../http-common";

    export default {
        name: "Login",
        data() {
            return {
                    usernameOrEmail: '',
                    password: ''
            }
        },
        methods:{
            logIn() {
                this.$store.dispatch('isLoading', true)
                AXIOS({
                    method: 'post',
                    url: '/auth/signin',
                    data: {
                        usernameOrEmail: this.usernameOrEmail,
                        password: this.password
                    }
                })
                    .then(response => {
                        this.$store.dispatch('setAuth', response)
                        localStorage.setItem('auth', 'Bearer ' + response.data.accessToken)
                        this.$router.push('/account')
                    })
                    .catch(e => {
                        this.$store.dispatch('setError', e.message)
                    })
            },
            signIn() {
                AXIOS({
                    method: 'get',
                    url: '/auth/signin',
                    headers: {
                        'Authorization': localStorage.getItem('auth')}
                })
                    .then(response => {
                        this.$store.dispatch('setAuth', response)
                        console.log(response.data)
                        this.$router.push('/account')
                    })
                    .catch(e => {
                        this.$store.dispatch('setError', e.message)
                        console.log(e.message)
                    })
            }
        },
        computed:{
            loading() {
                return this.$store.getters.getLoading
            },
            isAuth() {
                return this.$store.getters.getAuth
            },
            enableLs () {
                return localStorage.getItem('auth')
            }
        }
    }
</script>

<style scoped>

</style>
