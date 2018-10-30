<template>
    <div>
        <b-navbar toggleable="md" type="dark" variant="secondary">

            <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
            <b-navbar-brand to="/">Pac-Man Bank</b-navbar-brand>
            <b-collapse is-nav id="nav_collapse">
                <b-navbar-nav v-if="authClient">
                    <b-nav-item to="/browse">Browse</b-nav-item>
                    <b-nav-item-dropdown text="Accounts" right>
                        <b-dropdown-item to="/current">Current</b-dropdown-item>
                        <b-dropdown-item to="/history">History</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <b-nav-item to="/payment">Payment and Transaction</b-nav-item>
                    <b-nav-item to="/credit">Credit</b-nav-item>
                    <b-nav-item-dropdown text="Currency" right>
                        <b-dropdown-item to="/course">Course</b-dropdown-item>
                        <b-dropdown-item to="/change">Change</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown right v-if="auth">
                        <!-- Using button-content slot -->
                        <template slot="button-content">
                            <em>{{user.username}}</em>
                        </template>
                        <b-dropdown-item href="#">Profile</b-dropdown-item>
                        <b-dropdown-item href="#">Signout</b-dropdown-item>
                    </b-nav-item-dropdown>
                    <template v-if="auth">
                        <b-nav-item-dropdown
                                text="Accounts"
                                right
                                v-if="client">
                            <b-button size="sm"
                                      class="my-2 my-sm-0"
                                      type="submit">Accounts
                            </b-button>
                        </b-nav-item-dropdown>
                        <b-nav-item-dropdown text="Message" right>
                            <b-dropdown-item href="#" disabled>Unread</b-dropdown-item>
                            <b-dropdown-item href="#">Incoming</b-dropdown-item>
                            <b-dropdown-item href="#">Outgoing</b-dropdown-item>
                            <b-dropdown-item href="#" disabled>Trash</b-dropdown-item>
                        </b-nav-item-dropdown>
                        <b-button size="sm" class="my-2 my-sm-0" type="submit" @click="logOut">Log out</b-button>
                    </template>
                    <template v-else>
                        <b-btn v-b-modal.modal-sign-in>Sign in</b-btn>
                        <b-btn v-b-modal.modal-sign-up>Sign up</b-btn>
                    </template>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
        <div>
            <!-- Modal Component -->
            <b-modal id="modal-sign-in"
                     ref="modal"
                     title="Authorize... "
                     @ok="logIn"
                     @shown="clear">
                <form @submit.stop.prevent="logIn">
                    <b-form-input type="text"
                                  placeholder="Username or email"
                                  v-model="loginForm.usernameOrEmail"></b-form-input>
                    <b-form-input type="password"
                                  placeholder="Password"
                                  v-model="loginForm.password" my-3></b-form-input>
                </form>
            </b-modal>
            <b-modal id="modal-sign-up"
                     ref="modal"
                     title="Sign up... "
                     @ok="logIn"
                     @shown="clear">
                <registration></registration>
            </b-modal>
        </div>
    </div>
</template>

<script>

    import {AXIOS} from "../../http-common";
    import Registration from "../auth/Registration.vue";

    export default {
        name: "Navbar",
        components: {Registration},
        data() {
            return {
                loginForm: {
                    usernameOrEmail: '',
                    password: ''
                }
            }
        },
        computed: {
            user() {
                return this.$store.getters.getUser
            },
            auth() {
                return this.$store.getters.getAuth
            },
            client() {
                return this.$store.getters.getRoleClient
            },
            manager() {
                return this.$store.getters.getManager
            },
            accounts() {
                return this.$store.getters.getClientAccounts
            },
            authClient() {
                return this.$store.getters.getClient
            }
        },
        methods: {
            logOut() {
                localStorage.clear()
                this.$store.dispatch('clearAuth')
                    .then(() => {
                        this.$store.dispatch('clearAccounts')
                            .then(() => {
                                this.$router.push('/')
                            })
                    })

            },
            /**
             * Метод логирования.
             */
            logIn() {
                this.$store.dispatch('isLoading', true)
                this.$store.dispatch('setAuth', this.loginForm)
                    .then(() => {
                        console.log('in block')
                    })
                    .catch(e => {
                        this.$store.dispatch('setError', e.message)
                    })

            },
            clear() {
                this.usernameOrEmail = ''
                this.password = ''
            }
        }
    }

</script>

<style scoped>

</style>
