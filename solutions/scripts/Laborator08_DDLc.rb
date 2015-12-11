create 'country', {NAME=>'identification'}, {NAME=>'detail'}

create 'publishing_house', {NAME=>'identification'}, {NAME=>'detail'}, {NAME=>'address'}, {NAME=>'miscellanea'}

create 'collection', {NAME=>'identification'}, {NAME=>'referrence'}

create 'book', {NAME=>'identification'}, {NAME=>'detail'}, {NAME=>'version'}, {NAME=>'group'}

create 'format', {NAME=>'identification'}, {NAME=>'detail'}

create 'language', {NAME=>'identification'}, {'NAME'=> 'detail'}

create 'book_presentation', {NAME=>'identification'}, {NAME=>'referrence'}, {NAME=>'inventory'}

create 'writer', {NAME=>'appellation'}, {NAME=>'biography'}

create 'author', {NAME=>'referrence'}

create 'category', {NAME=>'identification'}, {NAME=>'detail'}

create 'category_content', {NAME=>'referrence'}

create 'supply_order_header', {NAME=>'identification'}, {NAME=>'situation'}, {NAME=>'producer'}

create 'supply_order_line', {NAME=>'referrence'}, {NAME=>'content'}

create 'user', {NAME=>'appellation'}, {NAME=>'identification'}, {NAME=>'contact'}, {NAME=>'category'}, {NAME=>'authentication'}

create 'invoice_header', {NAME=>'identification'}, {NAME=>'situation'}, {NAME=>'consumer'}

create 'invoice_line', {NAME=>'referrence'}, {NAME=>'content'}

create 'statistics', {NAME=>'expense'}
