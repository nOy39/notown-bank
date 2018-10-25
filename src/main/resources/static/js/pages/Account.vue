<!--todo сделать програмное возвращение на предыдущую страницу, сделать историю по счету-->
<template>
<div v-if="!loading">
    <div v-show="currentAcc">
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
                                    <dd class="col-sm-9"><em>{{ moment(currentAcc.updatedAt).format('LLL') }}</em></dd>
                                </dl>
                            </b-list-group-item>
                        </b-list-group>
                    </b-card>
                </b-tab>
                <b-tab no-body title="Picture 3">
                    <b-card-img bottom src="https://picsum.photos/600/200/?image=26" />
                    <b-card-footer>Picture 3 footer</b-card-footer>
                </b-tab>
                <b-tab title="Text">
                    <h5>This tab does not have the <code>no-body</code> prop set</h5>
                    Quis magna Lorem anim amet ipsum do mollit sit cillum voluptate ex nulla
                    tempor. Laborum consequat non elit enim exercitation cillum aliqua consequat
                    id aliqua. Esse ex consectetur mollit voluptate est in duis laboris ad sit
                    ipsum anim Lorem. Incididunt veniam velit elit elit veniam Lorem aliqua quis
                    ullamco deserunt sit enim elit aliqua esse irure.
                </b-tab>
            </b-tabs>
        </b-card>
    </div>
    <router-link to="/">main</router-link>
</div>
</template>

<script>
    import {AXIOS} from "../http-common";

    export default {
        name: "Account",
        beforeMount() {
            console.log('bm')
            this.fetchAccountData()
        },
        mounted() {
            console.log('m')
        },
        beforeUpdate() {
            console.log('bu')
            if (this.currentAcc.uniqCheckId != this.$route.params.id) {
                this.fetchAccountData()
            }
        },
        updated() {
            console.log('u')
        },
        computed: {
            acc() {
                return this.$route.params.id
            },
            currentAcc() {
                return this.$store.getters.getCurrentAcc
            },
            loading() {
                return this.$store.getters.getLoading
            },
            history() {
                return this.$store.getters.getHistory
            }
        },
        methods: {
            fetchAccountData() {
                this.$store.dispatch('isLoading', true)
                AXIOS({
                    method: 'get',
                    url: '/account/'+this.$route.params.id,
                    headers: {
                        'Authorization': sessionStorage.getItem('auth')
                    }
                })
                    .then(response => {
                        this.$store.dispatch('setCurrentAcc', response.data)
                            .then(() => console.log(response))
                    })
                    .then(() => {
                        AXIOS({
                            method: 'get',
                            url: '/logs/'+this.$route.params.id,
                            headers: {
                                'Authorization': sessionStorage.getItem('auth')
                            }
                        })
                            .then(response => {
                                this.$store.dispatch('setHistory', response.data)
                                    .then(() => {
                                        this.$store.dispatch('isLoading', false)
                                    })
                                    .catch(e => {
                                        this.$store.dispatch('setError', e.message)
                                    })
                            })
                            .catch(e => {
                                this.$store.dispatch('setError', e.message)
                            })
                    })
                    .catch(e => {
                        this.$store.dispatch('setError', e.message)
                    })
            }
        }
    }
</script>

<style scoped>

</style>
