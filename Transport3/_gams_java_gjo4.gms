Sets                                                           
      i   canning plants                                                   
      j   markets                                                          
                                                                           
 Parameters                                                                
      a(i)   capacity of plant i in cases                                  
      b(j)   demand at market j in cases                                   
      d(i,j) distance in thousands of miles                                
 Scalar f  freight in dollars per case per thousand miles;                 
                                                                           
$if not set gdxincname $abort 'no include file name for data file provided'
$gdxin %gdxincname%                                                        
$load i j a b d f                                                          
$gdxin                                                                     
                                                                           
 Parameter c(i,j)  transport cost in thousands of dollars per case ;       
                                                                           
            c(i,j) = f * d(i,j) / 1000 ;                                   
                                                                           
 Variables                                                                 
       x(i,j)  shipment quantities in cases                                
       z       total transportation costs in thousands of dollars ;        
                                                                           
 Positive Variable x ;                                                     
                                                                           
 Equations                                                                 
                                                                           
      cost        define objective function                                
      supply(i)   observe supply limit at plant i                          
      demand(j)   satisfy demand at market j ;                             
                                                                           
  cost ..        z  =e=  sum((i,j), c(i,j)*x(i,j)) ;                       
                                                                           
  supply(i) ..   sum(j, x(i,j))  =l=  a(i) ;                               
                                                                           
  demand(j) ..   sum(i, x(i,j))  =g=  b(j) ;                               
                                                                           
 Model transport /all/ ;                                                   
                                                                           
 Solve transport using lp minimizing z ;                                   
                                                                           
 Display x.l, x.m ;                                                        
                                                                           
