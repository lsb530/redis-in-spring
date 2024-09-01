import http from 'k6/http'
import { sleep } from 'k6'

export default function() {
    http.get('http://15.164.166.7:8080/boards')
}