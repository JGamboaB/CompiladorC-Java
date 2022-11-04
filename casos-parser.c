CASOS FOR

	- FUNCIONA
		; for(int x = 0; x < 5; x++){
			x++;
		}

		; for(;;){
		}


		; for(int x = 0; x < 5; x++){
			x++;
			break;

		}


	- SE CAE
		; for(int x = 0; x < 5; x++{
			x++;
		}

		; for int x = 0; x < 5; x++){
			x++;
		}

		; for(;;)
		}

		; for(;;){
		

------------------------------------------------------------------------
CASOS WHILE


	- FUNCIONA

		while(1){
			x++;
			x = 4+2+1;
		}


		while(1==1){
			x++;
			x = 4+2+1;
		}


		while(x=1){
			x++;
			x = 4+2+1;
		}
		

		while(1+4*4){
			x++;
			x = 4+2+1;
		}



	- SE CAE

		while(){
			x++;
		}

		while(int x=1){
			x++;
			x = 4+2+1;
		}



------------------------------------------------------------------------

CASOS DO-WHILE


	-FUNCIONA

		do{x++;} while (1);


		do{
			x++;
			if(x==x){
				x--;
			}
		} while (1);

	- SE CAE
			do
			x++;
			if(x==x){
				x--;
			}
		} while (1); 

			do{ // error falta '}' (caracter de recuperacion de compound_statement)
			x++; // usar try catch si falta el char de recuperacion
			if(x==x){
				x--;
			}
		 while (1);

		do{
			x++;
			if(x==x){
				x--;
			}
		} while 1);

		do{
			x++;
			if(x==x){
				x--;
			}
		} while (1;









