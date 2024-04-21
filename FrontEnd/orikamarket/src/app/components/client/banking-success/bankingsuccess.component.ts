import { Component, OnInit } from "@angular/core";
import { MessageService } from "primeng/api";

@Component({
    selector: 'app-bankingsuccess',
    templateUrl: './bankingsuccess.component.html',
    styleUrls: ['./bankingsucess.component.css'],
    providers: [MessageService]
})

export class BankingSuccessComponent implements OnInit {
    ngOnInit(): void {
        
    }
}